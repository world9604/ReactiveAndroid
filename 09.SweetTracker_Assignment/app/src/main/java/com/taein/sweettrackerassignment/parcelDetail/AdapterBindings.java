package com.taein.sweettrackerassignment.parcelDetail;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetailsVO;
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
    public static void bindImageUrl(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageDrawable(null);
        } else {
            Picasso.get().load(url).into(imageView);
        }
    }

    @BindingAdapter("bind:parcelLevel")
    public static void bindParcelLevel(TextView textView, int level) {
        ParcelInfoWithTrackingDetailsVO.ParcelLevel parcelLevel
                = ParcelInfoWithTrackingDetailsVO.ParcelLevel.convert(level);
        textView.setText(parcelLevel.getContent());
    }

    @BindingAdapter("bind:parcelDeliverTime")
    public static void bindParcelDeliverTime(TextView textView, String time) {
        // TODO: 2020-10-22 : 로컬라이제이션을 위해서 "도착에정시간 :" - resource로 빼야함
        final String templete = String.format("도착예정시간 : %s", time);
        textView.setText(templete);
    }
}