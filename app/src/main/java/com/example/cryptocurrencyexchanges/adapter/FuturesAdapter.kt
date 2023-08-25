package com.example.cryptocurrencyexchanges.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptocurrencyexchanges.data.MarketResponse

class FuturesAdapter: ListAdapter<MarketResponse.MarketData, MarketViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder.createViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}