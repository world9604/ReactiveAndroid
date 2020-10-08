package com.taein.reactiveandroid1;


import androidx.recyclerview.widget.RecyclerView;
import com.taein.reactiveandroid1.databinding.StockUpdateItemBinding;


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