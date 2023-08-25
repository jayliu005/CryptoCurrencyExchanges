package com.example.cryptocurrencyexchanges.network

import com.example.cryptocurrencyexchanges.data.MarketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface APIService {

    @GET("inquire/initial/market")
    @Headers("Content-Type: application/json")
    suspend fun getFutures(): Response<MarketResponse>

}