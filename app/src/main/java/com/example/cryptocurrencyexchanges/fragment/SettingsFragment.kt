package com.example.cryptocurrencyexchanges.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.cryptocurrencyexchanges.databinding.SettingsFragmentBinding
import com.example.cryptocurrencyexchanges.viewmodel.MainViewModel

class SettingsFragment: BaseFragment<SettingsFragmentBinding>(SettingsFragmentBinding::inflate) {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.textBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        mainViewModel.isHideBottomNavigation.value = true
    }

    override fun onDestroyView() {
        mainViewModel.isHideBottomNavigation.value = false
        super.onDestroyView()
    }
}