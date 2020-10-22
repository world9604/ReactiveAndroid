package com.taein.sweettrackerassignment.data.vo;

import com.google.gson.annotations.SerializedName;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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

    /*
     parcelLevel의 경우
     0 -> 송장만 등록 (register invoice only)
     1 -> 집하 (pick up)
     2-> 배송중 (In delivery)
     3 -> 배달출발 (Departure for delivery)
     4 -> 배달완료 (delivery completed)
     */
    @AllArgsConstructor
    public enum ParcelLevel {
        REGISTER_INVOICE("송장만 등록", 0),
        PICK_UP("집하", 1),
        IN_DELIVERY("배송중", 2),
        DEPARTURE("배달출발", 3),
        COMPLETED("배달완료", 4),
        NONE("조회된 건이 없음", 5);

        @Getter
        private String content;
        @Getter
        private int level;

        public static ParcelLevel convert(int level) {
            switch (level) {
                case 0 :
                    return REGISTER_INVOICE;
                case 1 :
                    return PICK_UP;
                case 2 :
                    return IN_DELIVERY;
                case 3 :
                    return DEPARTURE;
                case 4 :
                    return COMPLETED;
                default:
                    return NONE;
            }
        }
    }
}