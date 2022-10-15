package com.example.befit

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.befit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding1: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)


        // find bottom navigation
        val bottomNavigation : BottomNavigationView = binding1.bottomNavigation

        // define fragments here
        val listFragment: ListFragment = ListFragment()
        val dashboardFragment: DashboardFragment = DashboardFragment()

        // Handle Navigation selection
        bottomNavigation.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId){
                R.id.list_bottom_navigation -> fragment = listFragment
                R.id.dashboard_bottom_navigation -> fragment = dashboardFragment
            }
            swapFragment(fragment)
            true
        }
        // Set default selected fragment
        bottomNavigation.selectedItemId = R.id.list_bottom_navigation
    }


    private fun swapFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_frame_layout, fragment).commit()
    }
}