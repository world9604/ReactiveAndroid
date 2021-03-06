package com.taein.sweettrackerassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "parcel_Info")
public class ParcelInfo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "parcel_company_code")
    private String parcelCompanyCode;

    @ColumnInfo(name = "parcel_company_name")
    private String parcelCompanyName;

    @ColumnInfo(name = "parcel_invoice")
    private String parcelInvoice;

    @ColumnInfo(name = "parcel_level")
    private int parcelLevel;

    @ColumnInfo(name = "parcel_deliver_time")
    private String parcelDeliverTime;

    @ColumnInfo(name = "purchase_item_img")
    private String purchaseItemImg;

    @ColumnInfo(name = "purchase_item_name")
    private String purchaseItemName;

    @ColumnInfo(name = "purchase_item_date")
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