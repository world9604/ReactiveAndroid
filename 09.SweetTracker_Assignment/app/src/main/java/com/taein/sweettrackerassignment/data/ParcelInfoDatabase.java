package com.taein.sweettrackerassignment.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taein.sweettrackerassignment.data.dao.ParcelInfoDao;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;

@Database(entities = {ParcelInfo.class}, version = 1)
public abstract class ParcelInfoDatabase extends RoomDatabase {

    public abstract ParcelInfoDao stockUpdateDao();

    private static ParcelInfoDatabase INSTANCE;
    private static final Object sync = new Object();
    private static Context context;

    public static ParcelInfoDatabase getInstance(Context context) {
        ParcelInfoDatabase.context = context;

        if (INSTANCE == null) {
            synchronized (sync) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ParcelInfoDatabase.class, "stockApp.db")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
