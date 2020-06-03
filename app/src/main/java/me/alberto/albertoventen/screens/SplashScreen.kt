package me.alberto.albertoventen.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.alberto.albertoventen.R
import me.alberto.albertoventen.databinding.SplashScreenBinding
import me.alberto.albertoventen.util.DownloadSuccess

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
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)

        binding.viewModel = splashScreenViewModel
        binding.lifecycleOwner = this


        splashScreenViewModel.status.observe(this, Observer {
            if (it == DownloadSuccess) {
                Toast.makeText(this, "Download done", Toast.LENGTH_LONG).show()
            }
        })

        splashScreenViewModel.downloadPercent.observe(this, Observer {
            binding.prgressBar.progress = it.toInt()
        })

    }
}
