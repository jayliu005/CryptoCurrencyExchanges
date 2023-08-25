package com.example.cryptocurrencyexchanges.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.cryptocurrencyexchanges.R
import com.example.cryptocurrencyexchanges.databinding.MainActivityBinding
import com.example.cryptocurrencyexchanges.network.WebSockManager
import com.example.cryptocurrencyexchanges.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainActivityBinding: MainActivityBinding
    lateinit var navController: NavController

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityBinding = MainActivityBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        loadUI()
        loadViewModel()
        WebSockManager.connect()
    }

    private fun loadUI() {
        mainActivityBinding.bottomActionbar.itemIconTintList = null

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(mainActivityBinding.bottomActionbar, navController)
    }

    private fun loadViewModel() {
        mainViewModel.isHideBottomNavigation.observe(this) { isHideBottomNav ->
            mainActivityBinding.bottomActionbar.visibility = if(isHideBottomNav) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroy() {
        WebSockManager.close()
        super.onDestroy()
    }
}