package com.taein.retrofitandroid;


import androidx.recyclerview.widget.RecyclerView;
import com.taein.retrofitandroid.databinding.StockUpdateItemBinding;


public class StockUpdateViewHolder extends RecyclerView.ViewHolder {

    StockUpdateItemBinding binding;

    public StockUpdateViewHolder(StockUpdateItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(StockUpdate stockSymbol) {
        binding.setStockSymbol(stockSymbol);
    }
}