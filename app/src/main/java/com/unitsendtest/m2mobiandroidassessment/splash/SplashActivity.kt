package com.unitsendtest.m2mobiandroidassessment.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.unitsendtest.m2mobiandroidassessment.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onResume() {
        super.onResume()
        launchActivity()
    }

    private fun launchActivity() {
        handler.postDelayed(DELAY_MS){
            startActivity(MainActivity.createIntent(this))
        }
    }

    override fun onPause() {
        handler.removeCallbacksAndMessages(null)
        super.onPause()
    }

    companion object {

        private const val DELAY_MS = 2000L
    }
}