package com.taein.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.taein.roomandroid.databinding.ActivityMainBinding;
import com.taein.roomandroid.yahoo.RetrofitYahooServiceFactory;
import com.taein.roomandroid.yahoo.YahooService;
import com.taein.roomandroid.yahoo.data.repository.StockRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Observable;

public class MainActivity extends AppCompatActivity {

    @Getter
    String helloText;
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private StockDataAdapter stockDataAdapter;
    private StockRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

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

        Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap(i -> yahooService.yqlQuery(query, env).toObservable())
                .subscribeOn(Schedulers.io())
                .map(r -> r.getQuery().getResults().getQuote())
                .flatMap(Observable::fromIterable)
                .map(r -> StockUpdate.create(r))
                .doOnNext(this::saveStockUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stockUpdate -> {
                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
                    stockDataAdapter.add(stockUpdate);
                });

        // Repository 객체 얻기
        repository = StockRepository.getInstance(getApplication());
        repository.getStockUpdateById(1);
    }

    private void saveStockUpdate(StockUpdate stockUpdate) {
        log("saveStockUpdate", stockUpdate.getStockSymbol());
        repository.insert(stockUpdate);
    }

    private void log(Throwable throwable) {
        Log.e("APP", "Error", throwable);
    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }

    public static <T> Observable<T> v2(rx.Observable<T> source) {
        return toV2Observable(source);
    }
}