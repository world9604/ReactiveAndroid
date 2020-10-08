package com.taein.reactiveandroid2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.taein.reactiveandroid2.databinding.StockUpdateItemBinding;
import java.util.ArrayList;
import java.util.List;

public class StockDataAdapter extends RecyclerView.Adapter<StockUpdateViewHolder> {
    private final List<StockUpdate> data = new ArrayList<>();

    @Override
    public StockUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StockUpdateItemBinding binding = StockUpdateItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StockUpdateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StockUpdateViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(StockUpdate stockSymbol) {
        this.data.add(stockSymbol);
        notifyItemInserted(data.size() - 1);
    }

    /*void setItem(List<String> stockSymbol) {
        if (stockSymbol == null) {
            return;
        }
        this.data = stockSymbol;
        notifyDataSetChanged();
    }*/
}