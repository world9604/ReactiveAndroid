package com.taein.sweettrackerassignment.data.entity;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import java.lang.reflect.Type;
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

    private List<TrackingDetail> trackingDetails;
}
