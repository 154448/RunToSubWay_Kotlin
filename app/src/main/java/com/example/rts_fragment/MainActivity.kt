   package com.example.rts_fragment

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rts_fragment.databinding.ActivityMainBinding



   class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    //오프라인일때 기능 구현하다가...
    fun chkOnline(): String{
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.getActiveNetwork()
        return currentNetwork.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //AppBar setting
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.informationFragment, R.id.inputFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNav.setupWithNavController(navController)
        setContentView(binding.root)
    }


       override fun onSupportNavigateUp(): Boolean {
           val navController = binding.frgNav.getFragment<NavHostFragment>().navController
           return navController.navigateUp() || super.onSupportNavigateUp()
       }
}