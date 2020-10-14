package com.taein.sweettrackerassignment.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "tracking_detail")
public class TrackingDetail {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Getter @Setter
    private int id;

    @ColumnInfo(name = "time")
    @Getter
    private String time;

    @ColumnInfo(name = "where")
    @Getter
    private String where;

    @ColumnInfo(name = "status")
    @Getter
    private String status;

    public TrackingDetail(String time, String where, String status) {
        this.time = time;
        this.where = where;
        this.status = status;
    }
}
