package com.example.assignmentarchitecture.ui.activities.mainactivity

import com.example.assignmentarchitecture.data.MarkerDataManager
import com.example.assignmentarchitecture.domain.repository.Vehiclerepository
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainActivityViewModel(
        dataManager: MarkerDataManager,
        vehiclerepository: Vehiclerepository
    ): MainActivityViewModel {
        return MainActivityViewModel(dataManager, vehiclerepository)
    }

}

