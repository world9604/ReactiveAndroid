package com.taein.reactiveandroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taein.reactiveandroid1.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import lombok.Getter;

public class MainActivity extends AppCompatActivity {

    @Getter
    String helloText;
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private StockDataAdapter stockDataAdapter;

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

        Observable.just("APPL", "GOOGLE", "TWTR")
                .subscribe(stockSymbol -> stockDataAdapter.add(stockSymbol));
    }
}