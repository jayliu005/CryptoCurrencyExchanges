package com.example.cryptocurrencyexchanges.module

import com.example.cryptocurrencyexchanges.network.APIService
import com.example.cryptocurrencyexchanges.network.NetworkConfig
import com.example.cryptocurrencyexchanges.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApiService(): APIService =
        RetrofitFactory.retrofit(NetworkConfig.API_IRL, provideInterceptors()).create(APIService::class.java)

    @Provides
    fun provideInterceptors(): Array<Interceptor> = arrayOf(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    )
}