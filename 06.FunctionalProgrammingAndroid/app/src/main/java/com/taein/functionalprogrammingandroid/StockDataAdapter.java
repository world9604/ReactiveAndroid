package com.taein.functionalprogrammingandroid;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.taein.functionalprogrammingandroid.databinding.StockUpdateItemBinding;
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

    public void add(StockUpdate newStockUpdate) {
        for (StockUpdate stockUpdate : data) {
            if (stockUpdate.getStockSymbol().equals(newStockUpdate.getStockSymbol())) {
                if (stockUpdate.getPrice().equals(newStockUpdate.getPrice())) {
                    return;
                }
                break;
            }
        }
        this.data.add(0, newStockUpdate);
        notifyItemInserted(0);
    }

    /*void setItem(List<String> stockSymbol) {
        if (stockSymbol == null) {
            return;
        }
        this.data = stockSymbol;
        notifyDataSetChanged();
    }*/
}