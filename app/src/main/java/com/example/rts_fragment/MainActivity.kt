   package com.example.rts_fragment

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.rts_fragment.RetrofitData.GyeonguiObject
import com.example.rts_fragment.databinding.ActivityMainBinding
import com.example.rts_fragment.viewmodel.TraidDataViewModel
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


   class MainActivity : AppCompatActivity() {

    var isOff = 0
    private var upCount = 0
    private var downCount = 0
    lateinit var binding : ActivityMainBinding
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

}