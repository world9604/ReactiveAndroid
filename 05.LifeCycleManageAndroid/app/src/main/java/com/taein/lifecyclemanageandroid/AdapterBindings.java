package com.taein.lifecyclemanageandroid;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBindings {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<String> stockSymbol) {
        StockDataAdapter adapter = (StockDataAdapter) recyclerView.getAdapter();
        if (adapter != null) {
//            adapter.setItem(stockSymbol);
        }
    }
}