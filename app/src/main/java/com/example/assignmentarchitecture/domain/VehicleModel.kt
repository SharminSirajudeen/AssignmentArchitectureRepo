package com.example.assignmentarchitecture.domain

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

class VehicleModel : ClusterItem {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String = ""
    @SerializedName("description")
    @Expose
    var description: String = ""
    @SerializedName("latitude")
    @Expose
    var latitude: Double = 0.0
    @SerializedName("longitude")
    @Expose
    var longitude: Double = 0.0
    @SerializedName("batteryLevel")
    @Expose
    var batteryLevel: Int = 0
    @SerializedName("timestamp")
    @Expose
    var timestamp: String = ""
    @SerializedName("price")
    @Expose
    var price: Int = 0
    @SerializedName("priceTime")
    @Expose
    var priceTime: Int = 0
    @SerializedName("currency")
    @Expose
    var currency: String = ""

    override fun getTitle(): String {
        return ""
    }

    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getSnippet(): String {
        return ""
    }

}

