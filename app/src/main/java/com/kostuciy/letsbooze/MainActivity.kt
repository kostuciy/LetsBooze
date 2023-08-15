package com.kostuciy.letsbooze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kostuciy.letsbooze.companies.CompanyViewModel

class MainActivity : AppCompatActivity() {
//    val companyViewModel: CompanyViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)as NavHostFragment
        navController = navHostFragment.navController
    }
}