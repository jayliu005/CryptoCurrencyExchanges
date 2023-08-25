package com.example.cryptocurrencyexchanges.network

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrencyexchanges.data.MarketResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException


object WebSockManager {

    const val TAG = "WebSockManager"
    private var client: CryptoWebSocketClient? = null
    var marketList: List<MarketResponse.MarketData>? = null
    private val _updateMarketPrice = MutableLiveData<List<MarketResponse.MarketData>>()
    val updateMarketPrice = MediatorLiveData<List<MarketResponse.MarketData>>().apply {
        addSource(_updateMarketPrice) {
            postValue(_updateMarketPrice.value)
        }
    }
    var retryConnect = false

    private class CryptoWebSocketClient(url: String?) : WebSocketClient(URI(url)) {
        override fun onOpen(handshake: ServerHandshake) {
            Timber.d(TAG, "WebSocket open success")
            if (retryConnect) {
                retryConnect = false
                subscribe()
            }
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
            Timber.d(TAG, "WebSocket closed")
        }

        override fun onMessage(message: String) {
            val json = JSONObject(message)
            val jsonData = json.optJSONObject("data")
            if (jsonData != null) {
                parseData(jsonData)
            }
        }

        override fun onError(ex: Exception) {
            Timber.d(TAG, "Error message:${ex.message}")
        }
    }

    fun connect() {
        try {
            client = CryptoWebSocketClient(NetworkConfig.WS_URL)
            client?.connect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun close() {
        client?.close()
        client = null
    }

    fun subscribe() {
        val json = JsonObject()
        json.addProperty("op", "subscribe")
        val jsonArray = JsonArray()
        jsonArray.add("coinIndex")
        json.add("args", jsonArray)
        if (client?.isOpen == true) {
            client?.send(json.toString())
        } else {
            retryConnect = true
            connect()
        }
    }

    private fun parseData(jsonObj: JSONObject?) {

        val list = mutableListOf<MarketResponse.MarketData>()

        marketList?.forEach { marketData ->
            val data = MarketResponse.MarketData(marketData.symbol, marketData.isFutures, 0.0)
            val key = "${marketData.symbol}_1"
            val jsonData = jsonObj?.optJSONObject(key)
            if (jsonData?.optString("id")
                    .equals(marketData.symbol) && jsonData?.optInt("type") == 1
            ) {
                data.price = jsonData.optDouble("price")
                list.add(data)
            }
        }

        _updateMarketPrice.postValue(list)
    }
}