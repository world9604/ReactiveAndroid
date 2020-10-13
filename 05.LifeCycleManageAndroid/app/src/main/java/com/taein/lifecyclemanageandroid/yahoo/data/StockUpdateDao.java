package com.taein.lifecyclemanageandroid.yahoo.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.taein.lifecyclemanageandroid.StockUpdate;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface StockUpdateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StockUpdate stockUpdate);

    @Update
    void update(StockUpdate stockUpdate);

    @Delete
    void delete(StockUpdate stockUpdate);

    @Query("SELECT * FROM stock_updates WHERE id = (:id)")
    Single<StockUpdate> getStockUpdateById(int id);

    @Query("SELECT * FROM stock_updates ORDER BY date DESC LIMIT 50")
    Flowable<StockUpdate> getStockUpdates();

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
