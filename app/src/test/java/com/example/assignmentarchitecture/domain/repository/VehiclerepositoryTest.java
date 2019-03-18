package com.example.assignmentarchitecture.domain.repository;

import com.example.assignmentarchitecture.data.remote.Api;
import com.example.assignmentarchitecture.data.remote.GenericResponseHandler;
import com.example.assignmentarchitecture.domain.model.vehicles.VehicleModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.WeakHashMap;

import static org.mockito.Mockito.*;

public class VehiclerepositoryTest {
    @Mock
    Api api;
    @InjectMocks
    Vehiclerepository vehiclerepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVehicles() throws Exception {
        GenericResponseHandler<WeakHashMap<String, String>, List<VehicleModel>> result = vehiclerepository.getAllVehicles();
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void testGetVehicleDetails() throws Exception {
        GenericResponseHandler<Integer, VehicleModel> result = vehiclerepository.getVehicleDetails();
        Assert.assertNull(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme