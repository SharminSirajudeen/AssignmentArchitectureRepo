package com.example.assignmentarchitecture.ui.activities.mainactivity;

import android.support.v4.app.Fragment;
import com.example.assignmentarchitecture.framework.AppDataManager;
import com.example.assignmentarchitecture.domain.VehicleModel;
import com.example.assignmentarchitecture.usecase.VehicleUseCase;
import com.example.assignmentarchitecture.ui.base.BaseViewModels;
import com.google.android.gms.maps.model.LatLng;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class MainActivityTest {
    @InjectMocks
    MainActivity mainActivity;
    @Mock
    BaseViewModels myViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSetViewModel() throws Exception {
        mainActivity.setViewModel(new MainActivityViewModel(new AppDataManager(null, null, null), new VehicleUseCase(new AppDataManager(null, null, null))));
    }


    @Test
    public void testSetClickedVehicle() throws Exception {
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setBatteryLevel(34);
        mainActivity.setClickedVehicle(vehicleModel);
        VehicleModel result = mainActivity.getClickedVehicle();
        Assert.assertEquals(vehicleModel.getBatteryLevel(), result.getBatteryLevel());
    }



    private ArrayList<LatLng> getLatLongList() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(51.317049, 10.157386));
        return latLngs;
    }

    @Test
    public void testOnFragmentAttached() throws Exception {
        mainActivity.onFragmentAttached();
    }

    @Test
    public void testOnFragmentDetached() throws Exception {
        mainActivity.onFragmentDetached("tag");
    }

    @Test
    public void testOnSnackBarRetry() throws Exception {
        mainActivity.onSnackBarRetry();
    }

    @Test
    public void testAddFragment() throws Exception {
        mainActivity.addFragment(0, new Fragment(), true);
    }

    @Test
    public void testAddFragmentWithoutAnimation() throws Exception {
        mainActivity.addFragmentWithoutAnimation(0, new Fragment(), true);
    }

    @Test
    public void testAddFragmentWithoutExitAnimation() throws Exception {
        mainActivity.addFragmentWithoutExitAnimation(0, new Fragment(), true);
    }

    @Test
    public void testReplaceFragment() throws Exception {
        mainActivity.replaceFragment(1, new Fragment(), true);
    }

    @Test
    public void testReplaceFragmentWithoutAnimation() throws Exception {
        mainActivity.replaceFragmentWithoutAnimation(1, new Fragment(), true);
    }

    @Test
    public void testHasPermission() throws Exception {
        boolean result = mainActivity.hasPermission("permission");
        Assert.assertTrue(result);
    }

    @Test
    public void testHideKeyboard() throws Exception {
        mainActivity.hideKeyboard();
    }

    @Test
    public void testHideLoading() throws Exception {
        mainActivity.hideLoading();
    }


    @Test
    public void testRequestPermissionsSafely() throws Exception {
        mainActivity.requestPermissionsSafely(new String[]{"permissions"}, 0);
    }

    @Test
    public void testGetCurrentFragment() throws Exception {
        Fragment result = mainActivity.getCurrentFragment(0);
        Assert.assertNull(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme