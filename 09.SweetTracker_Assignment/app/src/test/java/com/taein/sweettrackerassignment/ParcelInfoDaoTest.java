package com.taein.sweettrackerassignment;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.taein.sweettrackerassignment.data.ParcelInfoDatabase;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Predicate;

@RunWith(AndroidJUnit4.class)
public class ParcelInfoDaoTest {

    private ParcelInfoDatabase mDatabase;
    private ParcelInfo testParcelInfo;

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                ParcelInfoDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        List<TrackingDetail> trackingDetails = new ArrayList<>();
        testParcelInfo = new ParcelInfo( "", "", "", "", "", "", "", trackingDetails);
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
        mDatabase.stockUpdateDao().insert(testParcelInfo);
        // When subscribing to the emissions of user
        mDatabase.stockUpdateDao()
                .getParcelInfoById(testParcelInfo.getId())
                .test()
                // assertValue asserts that there was only one emission
                .assertValue(new Predicate<ParcelInfo>() {
                    @Override
                    public boolean test(ParcelInfo parcelInfo) throws Exception {
                        // The emitted user is the expected one
                        return parcelInfo.getId() == testParcelInfo.getId() &&
                                parcelInfo.getParcelCompanyCode().equals(testParcelInfo.getParcelCompanyCode());
                    }
                });
    }
}
