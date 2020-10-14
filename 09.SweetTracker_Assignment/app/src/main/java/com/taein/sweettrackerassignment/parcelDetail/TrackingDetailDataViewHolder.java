package com.taein.sweettrackerassignment.parcelDetail;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import com.taein.sweettrackerassignment.databinding.TrackingDetailItemBinding;


public class TrackingDetailDataViewHolder extends RecyclerView.ViewHolder {

    TrackingDetailItemBinding binding;

    public TrackingDetailDataViewHolder(TrackingDetailItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(TrackingDetail trackingDetail) {
        binding.setTrackingDetail(trackingDetail);
//        holder.setIsStatusUpdate(stockUpdate.isTwitterStatusUpdate());
//        setIsStatusUpdate(trackingDetail.isTwitterStatusUpdate());
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