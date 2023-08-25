package com.example.cryptocurrencyexchanges.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.cryptocurrencyexchanges.R
import com.example.cryptocurrencyexchanges.databinding.InfoFragmentBinding

class InfoFragment: BaseFragment<InfoFragmentBinding>(InfoFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewBinding.imgSettings.setOnClickListener {
            it.findNavController().navigate(R.id.action_infoFragment_to_settingsFragment)
        }
    }
}