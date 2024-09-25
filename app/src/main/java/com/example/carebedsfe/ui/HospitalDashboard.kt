package com.example.carebedsfe.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.carebedsfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HospitalDashboard : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hospital_dashboard)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.organizer_bottom_navigation)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.organizer_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        //Set the default fragment if no saved instance
        if (savedInstanceState == null) {
            navController.navigate(R.id.hospitalPatientDashboard)

        }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_h_beds -> navController.navigate(R.id.hospitalBedsDashboard)
                R.id.nav_h_patients -> navController.navigate(R.id.hospitalPatientDashboard)
                R.id.nav_h_scan->navController.navigate(R.id.hospitalScanQRDashboard)
                //  R.id.nav_notifications -> navController.navigate(R.id.notificationsFragment)
                else -> false
            }
            true
        }
    }
}