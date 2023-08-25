package com.example.cryptocurrencyexchanges.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyexchanges.data.MarketResponse
import com.example.cryptocurrencyexchanges.databinding.MarketItemBinding

class MarketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var itemViewBinding: MarketItemBinding

    companion object {
        fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): MarketViewHolder {
            val itemView = MarketItemBinding.inflate(layoutInflater, parent, false)
            return MarketViewHolder(itemView)
        }
    }

    private constructor(binding: MarketItemBinding) : this(binding.root) {
        itemViewBinding = binding
    }

    fun bindTo(data: MarketResponse.MarketData) {
        itemViewBinding.textName.text = data.symbol
        itemViewBinding.textPrice.text = data.price.toString()
    }
}