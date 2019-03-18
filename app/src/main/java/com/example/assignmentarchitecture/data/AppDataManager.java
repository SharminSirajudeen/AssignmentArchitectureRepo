package com.example.assignmentarchitecture.data;

import android.content.Context;
import com.example.assignmentarchitecture.data.local.PreferencesHelper;
import com.example.assignmentarchitecture.data.remote.Api;
import com.example.assignmentarchitecture.domain.model.vehicles.VehicleModel;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AppDataManager implements MarkerDataManager {
    private final Api mApi;
    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, Api api, PreferencesHelper preferencesHelper) {
        mContext = context;
        mApi = api;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void clearAllPref() {
        mPreferencesHelper.clearAllPref();
    }

    @NotNull
    @Override
    public Call<List<VehicleModel>> getVehicles() {
        return mApi.getVehicles();
    }

    @NotNull
    @Override
    public Call<VehicleModel> getVehicleDetails(int vehicleId) {
        return mApi.getVehicleDetails(vehicleId);
    }

    @Override
    public boolean isLanguageSelected() {
        return mPreferencesHelper.isLanguageSelected();
    }

    @Override
    public void setLanguageSelected(boolean isLanguageSelected) {
        mPreferencesHelper.setLanguageSelected(isLanguageSelected);
    }
}
