package com.taein.sweettrackerassignment.parcelDetail;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.taein.sweettrackerassignment.parcelDetail.TrackingDetailDataAdapter;

public class AdapterBindings {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<String> stockSymbol) {
        TrackingDetailDataAdapter adapter = (TrackingDetailDataAdapter) recyclerView.getAdapter();
        if (adapter != null) {
//            adapter.setItem(stockSymbol);
        }
    }
}