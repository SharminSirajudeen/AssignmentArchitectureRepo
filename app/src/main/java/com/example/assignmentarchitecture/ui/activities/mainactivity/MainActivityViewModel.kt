package com.example.assignmentarchitecture.ui.activities.mainactivity

import com.example.assignmentarchitecture.R
import com.example.assignmentarchitecture.data.remote.Api
import com.example.assignmentarchitecture.data.remote.GenericResponseHandler
import com.example.assignmentarchitecture.domain.model.vehicles.VehicleModel
import com.example.assignmentarchitecture.domain.repository.Vehiclerepository
import com.example.assignmentarchitecture.ui.base.BaseViewModels
import com.example.assignmentarchitecture.utils.AppUtils
import java.util.*

class MainActivityViewModel(dataManager: Api, vehiclerepository: Vehiclerepository) :
    BaseViewModels<MainActivityNavigator>(dataManager) {
    var vehiclesApiHandler: GenericResponseHandler<WeakHashMap<String, String>, List<VehicleModel>>? = null
    var vehicleDetailsHandler: GenericResponseHandler<Int, VehicleModel>? = null

    init {
        vehiclesApiHandler = vehiclerepository.allVehicles
        vehicleDetailsHandler = vehiclerepository.vehicleDetails
    }

    override fun retryAPICalling() {

    }

    override fun loadData() {

    }

    fun callVehiclesApi() {
        if (AppUtils.getInstance().isInternetAvailable) {
            setIsLoading(true)
            vehiclesApiHandler!!.callApi(WeakHashMap())
        } else
            navigator.showSnackBar(R.string.please_check_internet_access)
    }

    fun getVehicleDetails(id: Int?) {
        if (AppUtils.getInstance().isInternetAvailable) {
            setIsLoading(true)
            vehicleDetailsHandler!!.callApi(id)
        } else
            navigator.showSnackBar(R.string.please_check_internet_access)
    }
}
