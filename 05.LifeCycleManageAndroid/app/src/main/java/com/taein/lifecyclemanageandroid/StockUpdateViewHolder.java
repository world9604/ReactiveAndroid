package com.taein.lifecyclemanageandroid;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import com.taein.lifecyclemanageandroid.databinding.StockUpdateItemBinding;


public class StockUpdateViewHolder extends RecyclerView.ViewHolder {

    StockUpdateItemBinding binding;

    public StockUpdateViewHolder(StockUpdateItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(StockUpdate stockSymbol) {
        binding.setStockSymbol(stockSymbol);
//        holder.setIsStatusUpdate(stockUpdate.isTwitterStatusUpdate());
        setIsStatusUpdate(stockSymbol.isTwitterStatusUpdate());
    }

    public void setIsStatusUpdate(boolean twitterStatusUpdate) {
        if (twitterStatusUpdate) {
            binding.stockItemTwitterStatus.setVisibility(View.VISIBLE);
            binding.stockItemPrice.setVisibility(View.GONE);
            binding.stockItemSymbol.setVisibility(View.GONE);
        } else {
            binding.stockItemTwitterStatus.setVisibility(View.GONE);
            binding.stockItemPrice.setVisibility(View.VISIBLE);
            binding.stockItemSymbol.setVisibility(View.VISIBLE);
        }
    }
}