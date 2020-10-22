package com.taein.sweettrackerassignment.data.vo;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParcelInfoWithTrackingDetailsVO {

    @SerializedName("parcelCompanyCode")
    private String parcelCompanyCode;

    @SerializedName("parcelCompanyName")
    private String parcelCompanyName;

    @SerializedName("parcelInvoice")
    private String parcelInvoice;

    @SerializedName("parcelLevel")
    private int parcelLevel;

    @SerializedName("parcelDeliverTime")
    private String parcelDeliverTime;

    @SerializedName("purchaseItemImg")
    private String purchaseItemImg;

    @SerializedName("purchaseItemName")
    private String purchaseItemName;

    @SerializedName("purchaseItemDate")
    private String purchaseItemDate;

    @SerializedName("trackingDetail")
    private List<TrackingDetail> trackingDetails;

    public ParcelInfoWithTrackingDetailsVO(String parcelCompanyCode, String parcelCompanyName,
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