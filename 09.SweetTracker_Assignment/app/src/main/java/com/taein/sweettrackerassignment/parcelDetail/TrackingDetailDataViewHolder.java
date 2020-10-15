package com.taein.sweettrackerassignment.parcelDetail;


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
}