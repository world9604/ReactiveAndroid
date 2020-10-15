package com.taein.sweettrackerassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Entity(tableName = "parcel_Info")
public class ParcelInfo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "parcel_company_code")
    @SerializedName("parcelCompanyCode")
    private String parcelCompanyCode;

    @ColumnInfo(name = "parcel_company_name")
    @SerializedName("parcelCompanyName")
    private String parcelCompanyName;

    @ColumnInfo(name = "parcel_level")
    @SerializedName("parcelLevel")
    private int parcelLevel;

    @ColumnInfo(name = "parcel_deliver_time")
    @SerializedName("parcelDeliverTime")
    private String parcelDeliverTime;

    @ColumnInfo(name = "purchase_item_img")
    @SerializedName("purchaseItemImg")
    private String purchaseItemImg;

    @ColumnInfo(name = "purchase_item_name")
    @SerializedName("purchaseItemName")
    private String purchaseItemName;

    @ColumnInfo(name = "purchase_item_date")
    @SerializedName("purchaseItemDate")
    private String purchaseItemDate;

    public ParcelInfo(String parcelCompanyCode, String parcelCompanyName,
                      int parcelLevel, String parcelDeliverTime, String purchaseItemImg,
                      String purchaseItemName, String purchaseItemDate) {
        this.parcelCompanyCode = parcelCompanyCode;
        this.parcelCompanyName = parcelCompanyName;
        this.parcelLevel = parcelLevel;
        this.parcelDeliverTime = parcelDeliverTime;
        this.purchaseItemImg = purchaseItemImg;
        this.purchaseItemName = purchaseItemName;
        this.purchaseItemDate = purchaseItemDate;
    }
}