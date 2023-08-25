package com.example.cryptocurrencyexchanges.repo

import com.example.cryptocurrencyexchanges.data.MarketResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MarketRepo {

    fun getMarket(): Flow<Response<MarketResponse>>
}