package com.example.cryptocurrencyexchanges.module

import com.example.cryptocurrencyexchanges.network.APIService
import com.example.cryptocurrencyexchanges.repo.MarketRepo
import com.example.cryptocurrencyexchanges.repo.MarketRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MarketModule {

    @Singleton
    @Provides
    fun provideMarketModule(apiService: APIService): MarketRepo =
        MarketRepoImpl(apiService)
}