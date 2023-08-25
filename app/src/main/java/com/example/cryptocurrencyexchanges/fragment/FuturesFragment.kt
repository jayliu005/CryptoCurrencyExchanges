package com.example.cryptocurrencyexchanges.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencyexchanges.adapter.FuturesAdapter
import com.example.cryptocurrencyexchanges.databinding.FuturesFragmentBinding
import com.example.cryptocurrencyexchanges.network.WebSockManager
import com.example.cryptocurrencyexchanges.viewmodel.MainViewModel

class FuturesFragment: BaseFragment<FuturesFragmentBinding>(FuturesFragmentBinding::inflate) {

    private val adapter = FuturesAdapter()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.list.adapter = adapter
        viewBinding.list.layoutManager = LinearLayoutManager(activity)
        init()
    }

    private fun init() {
        mainViewModel.updateMarketList.observe(viewLifecycleOwner) {
            adapter.submitList(it?.filter { marketData ->
                marketData.isFutures
            })
        }

        WebSockManager.updateMarketPrice.observe(viewLifecycleOwner) {
            adapter.submitList(it.filter { marketData ->
                marketData.isFutures
            }.sortedBy { marketData ->
                marketData.symbol
            })
        }
    }

}