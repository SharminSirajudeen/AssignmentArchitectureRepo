package com.example.assignmentarchitecture.framework;

import android.content.Context;
import com.example.assignmentarchitecture.data.local.IPreferences;
import com.example.assignmentarchitecture.data.remote.Api;
import com.example.assignmentarchitecture.domain.VehicleModel;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AppDataManager implements MarkerDataManager {
    private final Api mApi;
    private final Context mContext;
    private final IPreferences mIPreferences;

    @Inject
    public AppDataManager(Context context, Api api, IPreferences iPreferences) {
        mContext = context;
        mApi = api;
        mIPreferences = iPreferences;
    }

    @Override
    public void clearAllPref() {
        mIPreferences.clearAllPref();
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
        return mIPreferences.isLanguageSelected();
    }

    @Override
    public void setLanguageSelected(boolean isLanguageSelected) {
        mIPreferences.setLanguageSelected(isLanguageSelected);
    }
}
