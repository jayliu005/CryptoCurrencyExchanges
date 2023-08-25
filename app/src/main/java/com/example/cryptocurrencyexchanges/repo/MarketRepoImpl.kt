package com.example.cryptocurrencyexchanges.repo

import com.example.cryptocurrencyexchanges.data.MarketResponse
import com.example.cryptocurrencyexchanges.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MarketRepoImpl @Inject constructor(private val apiService: APIService) : MarketRepo {

    override fun getMarket(): Flow<Response<MarketResponse>> = flow {
        emit(apiService.getFutures())
    }
        .flowOn(Dispatchers.IO)
}