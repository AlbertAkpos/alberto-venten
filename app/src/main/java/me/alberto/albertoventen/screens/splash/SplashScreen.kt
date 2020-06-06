package me.alberto.albertoventen.screens.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.alberto.albertoventen.R
import me.alberto.albertoventen.activity.MainActivity
import me.alberto.albertoventen.databinding.SplashScreenBinding
import me.alberto.albertoventen.util.LoadingError
import java.util.*
import kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding

    private val splashScreenViewModel: SplashScreenViewModel by lazy {
        ViewModelProvider(
            this,
            SplashScreenViewModel.Factory(this)
        ).get(SplashScreenViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)

        binding.viewModel = splashScreenViewModel
        binding.lifecycleOwner = this

        observe()

    }


    private fun observe() {
        splashScreenViewModel.status.observe(this, Observer {
            if (it is LoadingError) {
                Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG).show()
            }
        })

        splashScreenViewModel.navigateToFilterFrag.observe(this, Observer {
            it ?: return@Observer
            if (it) {
                Timer().schedule(2000) {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }


            }
        })
    }


}
