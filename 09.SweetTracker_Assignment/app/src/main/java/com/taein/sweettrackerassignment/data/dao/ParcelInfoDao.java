package com.taein.sweettrackerassignment.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.ParcelInfoWithTrackingDetails;

@Dao
public interface ParcelInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ParcelInfo parcelInfo);

    @Update
    void update(ParcelInfo parcelInfo);

    @Delete
    void delete(ParcelInfo parcelInfo);

    @Transaction
    @Query("SELECT * FROM parcel_Info WHERE id = :id")
    ParcelInfoWithTrackingDetails getParcelInfoWithTrackingDetailsById(int id);

    @Query("SELECT * FROM parcel_Info ORDER BY purchase_item_date DESC LIMIT 50")
    ParcelInfoWithTrackingDetails getParcelInfos();

    //Rxjava
    /*@Query("SELECT * from user where id = :id LIMIT 1")
    public Flowable<User> loadUserById(int id);

    // Emits the number of users added to the database.
    @Insert
    public Maybe<Integer> insertLargeNumberOfUsers(List<User> users);

    // Makes sure that the operation finishes successfully.
    @Insert
    public Completable insertLargeNumberOfUsers(User... users);

    *//* Emits the number of users removed from the database. Always emits at
       least one user. *//*
    @Delete
    public Single<Integer> deleteUsers(List<User> users);*/
}
