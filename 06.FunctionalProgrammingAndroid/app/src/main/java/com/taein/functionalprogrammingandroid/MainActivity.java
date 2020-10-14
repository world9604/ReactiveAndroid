package com.taein.functionalprogrammingandroid;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taein.functionalprogrammingandroid.databinding.ActivityMainBinding;
import com.taein.functionalprogrammingandroid.yahoo.RetrofitYahooServiceFactory;
import com.taein.functionalprogrammingandroid.yahoo.YahooService;
import com.taein.functionalprogrammingandroid.yahoo.data.repository.StockRepository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

        String query = "select * from yahoo.finance.quote where symbol in ('YHOO', 'AAPL', 'GOOG', 'MSFT')";
        String env = "store://datatables.org/alltableswithkeys";

        final Configuration configuration = new ConfigurationBuilder()
                .setDebugEnabled(BuildConfig.DEBUG)
                .setOAuthConsumerKey("tTlvwBfqduVadKKEwMXDCmzA4")
                .setOAuthConsumerSecret("FiIOveHm9jLAtf0YSopWROeOFo3OA9VBM2CAuKwZ8AoL1gl4AK")
                .setOAuthAccessToken("195655474-QY8neLxXxqOsF8PGM8MYLsYGyQxQZA73S4qp0Sc2")
                .setOAuthAccessTokenSecret("lIiock0OTkR4TflFPb9pSMjLL8pN9JKIYKBhWMWwtxyMa")
                .build();

        final String[] trackingKeywords = {"Yahoo", "Google", "Microsoft"};
        final FilterQuery filterQuery = new FilterQuery()
                .track("Yahoo", "Google", "Microsoft")
                .language("en");

        Observable.merge(
            Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMapSingle(i -> yahooService.yqlQuery(query, env))
                .map(r -> r.getQuery().getResults().getQuote())
                .flatMap(Observable::fromIterable)
                .map(StockUpdate::create)
                .groupBy(stockUpdate -> stockUpdate.getStockSymbol())
                .flatMap(groupObservable -> groupObservable.distinctUntilChanged())
            ,
            observeTwitterStream(configuration, filterQuery)
                    .sample(2700, TimeUnit.MILLISECONDS)
                    .map(StockUpdate::create)
                    .filter(stockUpdate -> {
                        for (String keyword : trackingKeywords) {
                            if (stockUpdate.getTwitterStatus().contains(keyword)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .flatMapMaybe(update -> Observable.fromArray(trackingKeywords)
                            .filter(keyword -> update.getTwitterStatus().toLowerCase().contains(keyword.toLowerCase()))
                            .map(keyword -> update)
                            .firstElement()
                    )
        )
            .compose(bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .doOnError(ErrorHandler.get())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(error -> {
                Toast.makeText(this, "We couldn't reach internet - falling back to local data", Toast.LENGTH_SHORT).show();
            })
            .observeOn(Schedulers.io())
            .doOnNext(this::saveStockUpdate)
            .onExceptionResumeNext(
                    repository.getStockUpdates()
                            .take(1)
                            .flatMap(Observable::fromIterable)
            )
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