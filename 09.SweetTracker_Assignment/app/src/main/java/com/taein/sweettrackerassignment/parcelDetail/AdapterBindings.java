package com.taein.sweettrackerassignment.parcelDetail;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import com.taein.sweettrackerassignment.parcelDetail.TrackingDetailDataAdapter;

import java.util.List;

public class AdapterBindings {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, List<TrackingDetail> trackingDetails) {
        TrackingDetailDataAdapter adapter = (TrackingDetailDataAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(trackingDetails);
        }
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageDrawable(null);
        } else {
            Picasso.get().load(url).into(imageView);
        }
    }
}