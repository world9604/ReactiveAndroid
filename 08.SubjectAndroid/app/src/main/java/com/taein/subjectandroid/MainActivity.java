package com.taein.subjectandroid;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taein.subjectandroid.databinding.ActivityMainBinding;
import com.taein.subjectandroid.yahoo.RetrofitYahooServiceFactory;
import com.taein.subjectandroid.yahoo.YahooService;
import com.taein.subjectandroid.yahoo.data.repository.StockRepository;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static com.taein.subjectandroid.LocalItemPersistenceHandlingTransformer.addLocalItemPersistenceHandling;
import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Observable;

public class MainActivity extends RxAppCompatActivity {

    @Getter
    String helloText;
    private StockRepository repository;
    private ActivityMainBinding binding;
    private LinearLayoutManager layoutManager;
    private StockDataAdapter stockDataAdapter;
    private TextView noDataAvailableView;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        binding.stockUpdatesRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        binding.stockUpdatesRecyclerView.setLayoutManager(layoutManager);

        stockDataAdapter = new StockDataAdapter();
        binding.stockUpdatesRecyclerView.setAdapter(stockDataAdapter);

        Observable.just("Hello! Please use this app responsibly!")
                .subscribe(s -> helloText = s);

        YahooService yahooService = new RetrofitYahooServiceFactory().create();

        final Configuration configuration = new ConfigurationBuilder()
                .setDebugEnabled(BuildConfig.DEBUG)
                .setOAuthConsumerKey("tTlvwBfqduVadKKEwMXDCmzA4")
                .setOAuthConsumerSecret("FiIOveHm9jLAtf0YSopWROeOFo3OA9VBM2CAuKwZ8AoL1gl4AK")
                .setOAuthAccessToken("195655474-QY8neLxXxqOsF8PGM8MYLsYGyQxQZA73S4qp0Sc2")
                .setOAuthAccessTokenSecret("lIiock0OTkR4TflFPb9pSMjLL8pN9JKIYKBhWMWwtxyMa")
                .build();

        final Settings settings = Settings.get(this.getApplicationContext());

        Observable.merge(
                settings.getMonitoredSymbols()
                        .switchMap(symbols -> {
                            String query = createQuery(symbols);
                            String env = "store://datatables.org/alltableswithkeys";
                            return createFinancialStockUpdateObservable(yahooService, query, env);
                        }),
                settings.getMonitoredKeywords()
                        .switchMap(keywords -> {
                                    if (keywords.isEmpty()) {
                                        return Observable.never();
                                    }

                                    String[] trackingKeywords = keywords.toArray(new String[0]);
                                    final FilterQuery filterQuery = new FilterQuery()
                                            .track(trackingKeywords)
                                            .language("en");
                                    return createTweetStockUpdateObservable(configuration, trackingKeywords, filterQuery);
                                }
                        )
        )
                .groupBy(stockUpdate -> stockUpdate.getStockSymbol())
                .flatMap(groupObservable -> groupObservable.distinctUntilChanged())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .compose(addUiErrorHandling())
                .compose(addLocalItemPersistenceHandling(this))
                .doOnNext(update -> log(update))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stockUpdate -> {
                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
                    noDataAvailableView.setVisibility(View.GONE);
                    stockDataAdapter.add(stockUpdate);
                    binding.stockUpdatesRecyclerView.smoothScrollToPosition(0);
                }, error -> {
                    if (stockDataAdapter.getItemCount() == 0) {
                        noDataAvailableView.setVisibility(View.VISIBLE);
                    }
                });

        // Repository 객체 얻기
//        repository = StockRepository.getInstance(getApplication());
//        repository.getStockUpdateById(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String createQuery(List<String> symbols) {
        StringBuilder buffer = new StringBuilder("select * from yahoo.finance.quote where symbol in (");
        boolean first = true;
        for (String symbol : symbols) {
            if (!first) {
                buffer.append(",");
            }
            buffer.append("'").append(symbol).append("'");
            first = false;
        }
        buffer.append(")");
        return buffer.toString();
    }

    private Observable<StockUpdate> createFinancialStockUpdateObservable(YahooService yahooService, String query, String env) {
        return Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMapSingle(i -> yahooService.yqlQuery(query, env))
                .map(r -> r.getQuery().getResults().getQuote())
                .flatMap(Observable::fromIterable)
                .map(StockUpdate::create)
                .groupBy(stockUpdate -> stockUpdate.getStockSymbol())
                .flatMap(groupObservable -> groupObservable.distinctUntilChanged());
    }

    private Observable<StockUpdate> createTweetStockUpdateObservable(Configuration config, String[] trackingKeywords, FilterQuery filterQuery) {
        return observeTwitterStream(config, filterQuery)
                .sample(2700, TimeUnit.MILLISECONDS)
                .map(StockUpdate::create)
                .filter(containsAnyOfKeywords(trackingKeywords))
                .flatMapMaybe(skipTweetsThatDoNotContainKeywords(trackingKeywords));
    }

    @NonNull
    private Predicate<StockUpdate> containsAnyOfKeywords(String[] trackingKeywords) {
        return stockUpdate -> {
            for (String keyword : trackingKeywords) {
                if (stockUpdate.getTwitterStatus().contains(keyword)) {
                    return true;
                }
            }
            return false;
        };
    }

    @NonNull
    private Function<StockUpdate, MaybeSource<? extends StockUpdate>> skipTweetsThatDoNotContainKeywords(String[] trackingKeywords) {
        return update -> Observable.fromArray(trackingKeywords)
                .filter(keyword -> update.getTwitterStatus().toLowerCase().contains(keyword.toLowerCase()))
                .map(keyword -> update)
                .firstElement();
    }

    @NonNull
    private ObservableTransformer<StockUpdate, StockUpdate> addUiErrorHandling() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnError(MainActivity.this::showToastErrorNotificationMethod)
                .observeOn(Schedulers.io());
    }

    private void showToastErrorNotificationMethod(Throwable error) {
        Toast.makeText(this, "We couldn't reach internet - falling back to local data",
                Toast.LENGTH_SHORT)
                .show();
    }

    Observable<Status> observeTwitterStream(Configuration configuration, FilterQuery filterQuery) {
        return Observable.create(emitter -> {
           final TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();

           emitter.setCancellable(() -> {
               Schedulers.io().scheduleDirect(() -> twitterStream.cleanUp());
           });

            StatusListener listener = new StatusListener() {
                @Override
                public void onStatus(Status status) {
                    emitter.onNext(status);
                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

                @Override
                public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

                @Override
                public void onScrubGeo(long userId, long upToStatusId) {}

                @Override
                public void onStallWarning(StallWarning warning) {}

                @Override
                public void onException(Exception ex) {
                    emitter.onError(ex);
                }
            };

            twitterStream.addListener(listener);
            twitterStream.filter(filterQuery);
        });
    }

    private void saveStockUpdate(StockUpdate stockUpdate) {
        log("saveStockUpdate", stockUpdate.getStockSymbol());
        repository.insert(stockUpdate);
    }

    public static <T> Observable<T> v2(rx.Observable<T> source) {
        return toV2Observable(source);
    }

    private void log(Throwable throwable) {
        Log.e("APP", "Error on " + Thread.currentThread().getName() + ":", throwable);
    }

    private void log(String stage, Throwable throwable) {
        Log.e("APP", stage + ":" + Thread.currentThread().getName() + ": error", throwable);
    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage, int item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage, long item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }

    private void log(StockUpdate update) {
        Log.d("APP", Thread.currentThread().getName() + ":" + update.toString());
    }

    private void log(long value) {
        Log.d("APP", Thread.currentThread().getName() + ":" + value);
    }
}