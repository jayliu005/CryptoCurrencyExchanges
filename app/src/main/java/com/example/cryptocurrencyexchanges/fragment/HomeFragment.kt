package com.example.cryptocurrencyexchanges.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.cryptocurrencyexchanges.R
import com.example.cryptocurrencyexchanges.databinding.HomeFragmentBinding
import com.example.cryptocurrencyexchanges.network.WebSockManager
import com.example.cryptocurrencyexchanges.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class HomeFragment: BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val spotFragment = SpotFragment()
    private val futuresFragment = FuturesFragment()
    private var fragmentManager: FragmentManager? = null
    private var itemPosition = 0
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager = activity?.supportFragmentManager.let {
            it?.beginTransaction()
                ?.add(R.id.list_container, spotFragment)
                ?.add(R.id.list_container, futuresFragment)
                ?.hide(futuresFragment)
                ?.commitAllowingStateLoss()
            it
        }

        init()
    }

    private fun init() {
        viewBinding.tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                fragmentManager?.beginTransaction().let {
                    if (tab?.position == 0) {
                        it?.hide(futuresFragment)?.show(spotFragment)
                    }
                    else {
                        it?.hide(spotFragment)?.show(futuresFragment)
                    }
                    it?.commitAllowingStateLoss()
                }
                itemPosition = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        mainViewModel.getMarketList().observe(viewLifecycleOwner) { response ->
            if(response.body()?.message?.equals("Loading") == true) {
                viewBinding.progress.visibility = View.VISIBLE
            }
            else {
                response.body()?.data?.also {
                    WebSockManager.marketList = it
                    WebSockManager.subscribe()
                    viewBinding.progress.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewBinding.tabLayout.selectTab(viewBinding.tabLayout.getTabAt(itemPosition))
    }

    override fun onPause() {
        fragmentManager?.beginTransaction()?.remove(spotFragment)?.remove(futuresFragment)?.commit()
        super.onPause()
    }
}