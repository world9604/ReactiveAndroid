package com.taein.sweettrackerassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Entity(tableName = "tracking_detail")
public class TrackingDetail {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tracking_id")
    @SerializedName("parcelCompanyCode")
    private int id;

    @ColumnInfo(name = "tracking_time")
    @SerializedName("time")
    private String time;

    @ColumnInfo(name = "tracking_where")
    @SerializedName("where")
    private String where;

    @ColumnInfo(name = "tracking_status")
    @SerializedName("status")
    private String status;

    @ColumnInfo(name = "parcel_info_id")
    @SerializedName("parcelInfoId")
    private String parcelInfoId;

    public TrackingDetail(String time, String where, String status) {
        this.time = time;
        this.where = where;
        this.status = status;
    }
}
