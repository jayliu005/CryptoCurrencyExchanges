package com.example.cryptocurrencyexchanges.data

import com.google.gson.annotations.SerializedName

data class MarketResponse(
    @SerializedName("code") val code:Int,
    @SerializedName("msg") val message: String,
    @SerializedName("time") val time: Long,
    @SerializedName("data") val data: List<MarketData>,
    @SerializedName("success") val isSuccess: Boolean
) {
    data class MarketData(
        @SerializedName("symbol") val symbol:String,
        @SerializedName("future") val isFutures: Boolean,
        var price: Double = 0.0
    )
}