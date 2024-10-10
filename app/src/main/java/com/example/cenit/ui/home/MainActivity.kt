package com.example.cenit.ui.home

import android.os.Bundle
import android.view.View
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
        setupFabNavigation()  // Añadir la funcionalidad de navegación para el FAB
        setupBackNavigation()  // Configurar la funcionalidad de navegación hacia atrás
    }

    private fun setupBackNavigation() {
        binding.ivBack.setOnClickListener {
            navController.popBackStack()  // Navega hacia atrás en la pila de navegación
        }
    }

    private fun setupFabNavigation() {
        // Configurar el FAB para navegar a onBoardingFragment
        binding.fab.setOnClickListener {
            // Navegar a onBoardingFragment
            navController.navigate(R.id.onBoardingFragment)
        }
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)

        // Llama a la función que se encarga de actualizar el título
        setupToolbarTitleChange(navController)
    }

    private fun setupToolbarTitleChange(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitle.text = getToolbarTitle(destination.id)
            updateUIVisibility(destination.id)
            // Mostrar u ocultar el ImageView de retroceso
            binding.ivBack.visibility = if (destination.id == R.id.onBoardingFragment) View.VISIBLE else View.GONE
        }
    }

    private fun getToolbarTitle(destinationId: Int): String {
        return when (destinationId) {
            R.id.runningProofsFragment -> getString(R.string.RunningProofs_Header)
            R.id.certificatesFragment -> getString(R.string.Certificates_Header)
            R.id.devicesFragment -> getString(R.string.Devices_Header)
            R.id.notificationsFragment -> getString(R.string.Notifications_Header)
            R.id.accountFragment -> getString(R.string.Account_Header)
            R.id.onBoardingFragment -> getString(R.string.OnBoarding_Header)
            else -> getString(R.string.app_name)
        }
    }

    private fun updateUIVisibility(destinationId: Int) {
        val isOnboarding = destinationId == R.id.onBoardingFragment
        binding.bottomNavView.visibility = if (isOnboarding) View.GONE else View.VISIBLE
        binding.fab.visibility = if (isOnboarding) View.GONE else View.VISIBLE
    }
}