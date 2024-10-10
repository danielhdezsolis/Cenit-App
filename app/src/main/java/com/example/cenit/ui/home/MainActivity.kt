package com.example.cenit.ui.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cenit.R
import com.example.cenit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)

        // Llama a la función que se encarga de actualizar el título
        setupToolbarTitleChange(binding, navController)

    }

    private fun setupToolbarTitleChange(binding: ActivityMainBinding, navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Usar binding para acceder al TextView en el FrameLayout de la toolbar
            binding.toolbarTitle.apply {
                text = when (destination.id) {
                    R.id.runningProofsFragment -> getString(R.string.RunningProofs_Header)
                    R.id.certificatesFragment -> getString(R.string.Certificates_Header)
                    R.id.devicesFragment -> getString(R.string.Devices_Header)
                    R.id.notificationsFragment -> getString(R.string.Notifications_Header)
                    R.id.accountFragment -> getString(R.string.Account_Header)
                    R.id.onBoardingFragment -> getString(R.string.OnBoarding_Header)
                    else -> getString(R.string.app_name) // Texto por defecto
                }
            }
        }
    }
}