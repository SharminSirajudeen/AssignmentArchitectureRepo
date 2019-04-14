package com.example.assignmentarchitecture.presentation.activities.mainactivity

import com.example.assignmentarchitecture.framework.MarkerDataManager
import com.example.assignmentarchitecture.usecase.VehicleUseCase
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainActivityViewModel(
        dataManager: MarkerDataManager,
        vehicleUseCase: VehicleUseCase
    ): MainActivityViewModel {
        return MainActivityViewModel(dataManager, vehicleUseCase)
    }

}

