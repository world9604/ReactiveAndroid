package com.taein.roomandroid3;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.taein.roomandroid3.yahoo.data.StockDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Date;

import io.reactivex.functions.Predicate;

@RunWith(AndroidJUnit4.class)
public class StockUpdateDaoTest {

    private StockDatabase mDatabase;
    private StockUpdate testStockUpdate;

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                StockDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        testStockUpdate = new StockUpdate(1, "stockSymbol_test",
                new BigDecimal(21), new Date());
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Test
    public void insertAndGetStockUpdateById() {
        // Given that we have a user in the data source
        mDatabase.stockUpdateDao().insert(testStockUpdate);
        // When subscribing to the emissions of user
        mDatabase.stockUpdateDao()
                .getStockUpdateById(testStockUpdate.getId())
                .test()
                // assertValue asserts that there was only one emission
                .assertValue(new Predicate<StockUpdate>() {
                    @Override
                    public boolean test(StockUpdate stockUpdate) throws Exception {
                        // The emitted user is the expected one
                        return stockUpdate.getId() == testStockUpdate.getId() &&
                                stockUpdate.getPrice().equals(testStockUpdate.getPrice());
                    }
                });
    }
}
