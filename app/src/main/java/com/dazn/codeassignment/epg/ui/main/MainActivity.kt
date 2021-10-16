package com.dazn.codeassignment.epg.ui.main

import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dazn.codeassignment.epg.R
import com.dazn.codeassignment.epg.databinding.ActivityMainBinding
import com.dazn.codeassignment.epg.ui.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {


    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fra.id) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = { layoutInflater ->
        ActivityMainBinding.inflate(layoutInflater)
    }


}