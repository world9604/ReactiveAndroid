package com.taein.sweettrackerassignment;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.taein.sweettrackerassignment.data.ParcelInfoDatabase;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ParcelInfoDaoTest {

    private ParcelInfoDatabase mDatabase;
    private ParcelInfo testParcelInfo;
    private List<TrackingDetail> testTrackingDetails;

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                ParcelInfoDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        testParcelInfo = new ParcelInfo( "companyCode", "companyName", 0, "parcelDeliverTime",
                "purchaseItemImg", "purchaseItemName", "purchaseItemDate");
        testTrackingDetails = new ArrayList<>();
        testTrackingDetails.add(new TrackingDetail("time", "where", "status"));
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
        mDatabase.ParcelInfoDao().insert(testParcelInfo);
        // When subscribing to the emissions of user
        ParcelInfoWithTrackingDetails parcelInfoWithTrackingDetails = mDatabase.ParcelInfoDao()
                .getParcelInfoWithTrackingDetailsById(testParcelInfo.getId());

        assertThat(testParcelInfo.getId(), is(parcelInfoWithTrackingDetails.getParcelInfo().getId()));

        /*mDatabase.ParcelInfoDao()
                .getParcelVOById(testParcelVO.getId())
                .test()
                // assertValue asserts that there was only one emission
                .assertValue(new Predicate<ParcelInfo>() {
                    @Override
                    public boolean test(ParcelInfo parcelInfo) throws Exception {
                        // The emitted user is the expected one
                        return parcelInfo.getId() == testParcelVO.getId() &&
                                parcelInfo.getParcelCompanyCode().equals(testParcelVO.getParcelCompanyCode());
                    }
                });*/
    }
}
