package com.taein.sweettrackerassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "stock_updates")
public class ParcelInfo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Getter @Setter
    private int id;

    @ColumnInfo(name = "parcelCompanyCode")
    @Getter
    private String parcelCompanyCode;

    @ColumnInfo(name = "parcelCompanyName")
    @Getter
    private String parcelCompanyName;

    @ColumnInfo(name = "parcelLevel")
    @Getter
    private int parcelLevel;

    @ColumnInfo(name = "parcelDeliverTime")
    @Getter
    private String parcelDeliverTime;

    @ColumnInfo(name = "purchaseItemImg")
    @Getter
    private String purchaseItemImg;

    @ColumnInfo(name = "purchaseItemName")
    @Getter
    private String purchaseItemName;

    @ColumnInfo(name = "purchaseItemDate")
    @Getter
    private String purchaseItemDate;

    @ColumnInfo(name = "trackingDetail")
    @ForeignKey(
            entity = ParcelInfo.class,
            parentColumns = "id",
            childColumns = "trackingDetails"
    )
    @Getter
    private List<TrackingDetail> trackingDetails;

    public ParcelInfo(String parcelCompanyCode, String parcelCompanyName, int parcelLevel, String parcelDeliverTime, String purchaseItemImg, String purchaseItemName, String purchaseItemDate, List<TrackingDetail> trackingDetails) {
        this.parcelCompanyCode = parcelCompanyCode;
        this.parcelCompanyName = parcelCompanyName;
        this.parcelLevel = parcelLevel;
        this.parcelDeliverTime = parcelDeliverTime;
        this.purchaseItemImg = purchaseItemImg;
        this.purchaseItemName = purchaseItemName;
        this.purchaseItemDate = purchaseItemDate;
        this.trackingDetails = trackingDetails;
    }

    /*public static ParcelInfo create(ParcelInfo newParcelInfo) {
        return new ParcelInfo();
    }*/
}