package com.example.cenit.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cenit.R
import com.example.cenit.ui.home.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Espera un tiempo antes de navegar a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.enter_anim, R.anim.exit_anim)
            startActivity(intent, options.toBundle())
            finish() // Finaliza la SplashActivity para que no vuelva a aparecer al presionar atrás
        }, 2000) // 2 segundos de espera
    }
}