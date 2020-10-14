package com.taein.refectoringandroid.yahoo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taein.refectoringandroid.StockUpdate;

@Database(entities = {StockUpdate.class}, version = 1)
public abstract class StockDatabase extends RoomDatabase {

    public abstract StockUpdateDao stockUpdateDao();

    private static StockDatabase INSTANCE;
    private static final Object sync = new Object();
    private static Context context;

    public static StockDatabase getInstance(Context context) {
        StockDatabase.context = context;

        if (INSTANCE == null) {
            synchronized (sync) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StockDatabase.class, "stockApp.db")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
