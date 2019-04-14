package com.example.assignmentarchitecture.data.remote

import com.example.assignmentarchitecture.domain.VehicleModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("vehicles")
    fun getVehicles(): Call<List<VehicleModel>>

    @GET("vehicles/{vehicle_id}")
    fun getVehicleDetails(@Path("vehicle_id") vehicleId: Int): Call<VehicleModel>

}