package com.example.assignmentarchitecture.usecase;

import com.example.assignmentarchitecture.data.remote.Api;
import com.example.assignmentarchitecture.data.remote.GenericResponseHandler;
import com.example.assignmentarchitecture.domain.VehicleModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.WeakHashMap;

public class VehicleUseCaseTest {
    @Mock
    Api api;
    @InjectMocks
    VehicleUseCase vehicleUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVehicles() throws Exception {
        GenericResponseHandler<WeakHashMap<String, String>, List<VehicleModel>> result = vehicleUseCase.getAllVehicles();
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void testGetVehicleDetails() throws Exception {
        GenericResponseHandler<Integer, VehicleModel> result = vehicleUseCase.getVehicleDetails();
        Assert.assertNull(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme