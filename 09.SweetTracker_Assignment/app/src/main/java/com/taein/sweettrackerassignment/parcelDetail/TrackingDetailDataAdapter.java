package com.taein.sweettrackerassignment.parcelDetail;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.taein.sweettrackerassignment.databinding.TrackingDetailItemBinding;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;

import java.util.ArrayList;
import java.util.List;

public class TrackingDetailDataAdapter extends RecyclerView.Adapter<TrackingDetailDataViewHolder> {
    private final List<TrackingDetail> data = new ArrayList<>();

    @Override
    public TrackingDetailDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TrackingDetailItemBinding binding = TrackingDetailItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrackingDetailDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TrackingDetailDataViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<TrackingDetail> newTrackingDetails) {
        this.data.addAll(0, newTrackingDetails);
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