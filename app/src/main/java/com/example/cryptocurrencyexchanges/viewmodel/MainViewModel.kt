package com.example.cryptocurrencyexchanges.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyexchanges.data.MarketResponse
import com.example.cryptocurrencyexchanges.repo.MarketRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val marketRepo: MarketRepo) : ViewModel() {

    private val TAG = "MainViewModel"

    val isHideBottomNavigation = MutableLiveData<Boolean>()
    private val _updateMarketList = MutableLiveData<List<MarketResponse.MarketData>>()
    val updateMarketList = MediatorLiveData<List<MarketResponse.MarketData>>().apply {
        addSource(_updateMarketList) {
            postValue(_updateMarketList.value)
        }
    }

    fun getMarketList() =
        marketRepo.getMarket()
            .map {
                _updateMarketList.postValue(it.body()?.data)
                it
            }
            .onStart {
                emit(
                    retrofit2.Response.success(
                        MarketResponse(
                            code = 1,
                            message = "Loading",
                            time = 0,
                            data = emptyList(),
                            isSuccess = true
                        )
                    )
                )
            }
            .catch { Timber.d(TAG, it.message.toString()) }
            .asLiveData(viewModelScope.coroutineContext)
}