package com.example.cryptocurrencyexchanges.adapter

import android.text.TextUtils
import androidx.recyclerview.widget.DiffUtil
import com.example.cryptocurrencyexchanges.data.MarketResponse

class DiffUtilCallback: DiffUtil.ItemCallback<MarketResponse.MarketData>() {
    override fun areItemsTheSame(oldItem: MarketResponse.MarketData, newItem: MarketResponse.MarketData): Boolean {
        return TextUtils.equals(oldItem.symbol, newItem.symbol)
    }
    override fun areContentsTheSame(oldItem: MarketResponse.MarketData, newItem: MarketResponse.MarketData): Boolean {
        return oldItem.price == newItem.price
    }
}