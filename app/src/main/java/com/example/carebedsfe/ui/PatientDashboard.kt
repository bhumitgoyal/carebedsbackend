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

class PatientDashboard : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_dashboard)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
         //Set the default fragment if no saved instance
        if (savedInstanceState == null) {
            navController.navigate(R.id.patientDashboard)

        }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> navController.navigate(R.id.patientDashboard)
                R.id.nav_summary -> navController.navigate(R.id.patientSummary)
                R.id.nav_hospital->navController.navigate(R.id.patientHospitalDashboard)
                //  R.id.nav_notifications -> navController.navigate(R.id.notificationsFragment)
                else -> false
            }
            true
        }

    }
}