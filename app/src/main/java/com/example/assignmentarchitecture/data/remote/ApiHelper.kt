package com.example.assignmentarchitecture.data.remote

import com.example.assignmentarchitecture.domain.VehicleModel
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelper @Inject
constructor(internal var api: Api) : Api {

    override fun getVehicles(): Call<List<VehicleModel>> {
        return api.getVehicles()
    }

    override fun getVehicleDetails(vehicleId: Int): Call<VehicleModel> {
        return api.getVehicleDetails(vehicleId)
    }
}
