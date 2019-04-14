package com.example.assignmentarchitecture.usecase

import android.arch.lifecycle.MutableLiveData
import com.example.assignmentarchitecture.data.remote.Api
import com.example.assignmentarchitecture.data.remote.GenericResponseHandler
import com.example.assignmentarchitecture.data.remote.NotOkException
import com.example.assignmentarchitecture.domain.VehicleModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class VehicleUseCase @Inject
constructor(private val api: Api) {

    val allVehicles: GenericResponseHandler<WeakHashMap<String, String>, List<VehicleModel>>
        get() = object : GenericResponseHandler<WeakHashMap<String, String>, List<VehicleModel>>() {
            override fun proceeedApiCall(
                successResponseLiveData: MutableLiveData<List<VehicleModel>>,
                failureResponseLiveData: MutableLiveData<Throwable>,
                params: WeakHashMap<String, String>?
            ) {
                api.getVehicles().enqueue(object : Callback<List<VehicleModel>> {
                    override fun onResponse(call: Call<List<VehicleModel>>, response: Response<List<VehicleModel>>) {
                        if (response.isSuccessful)
                            successResponseLiveData.postValue(response.body())
                        else
                            failureResponseLiveData.postValue(NotOkException.newFromResponse(response))
                    }

                    override fun onFailure(call: Call<List<VehicleModel>>, t: Throwable) {
                        failureResponseLiveData.postValue(t)
                    }
                })
            }
        }

    val vehicleDetails: GenericResponseHandler<Int, VehicleModel>?
        get() = object : GenericResponseHandler<Int, VehicleModel>() {
            override fun proceeedApiCall(
                successResponseLiveData: MutableLiveData<VehicleModel>,
                failureResponseLiveData: MutableLiveData<Throwable>,
                params: Int?
            ) {
                api.getVehicleDetails(params!!).enqueue(object : Callback<VehicleModel> {
                    override fun onResponse(call: Call<VehicleModel>, response: Response<VehicleModel>) {
                        if (response.isSuccessful)
                            successResponseLiveData.postValue(response.body())
                        else
                            failureResponseLiveData.postValue(NotOkException.newFromResponse(response))
                    }

                    override fun onFailure(call: Call<VehicleModel>, t: Throwable) {
                        failureResponseLiveData.postValue(t)
                    }
                })
            }
        }
}
