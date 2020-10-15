package com.taein.sweettrackerassignment.data.vo;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.google.gson.annotations.SerializedName;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParcelInfoWithTrackingDetails {

    @Embedded
    private ParcelInfo parcelInfo;

    @Relation(
            parentColumn = "id",
            entityColumn = "parcel_info_id"
    )

    @SerializedName("trackingDetail")
    private List<TrackingDetail> trackingDetails;
}
