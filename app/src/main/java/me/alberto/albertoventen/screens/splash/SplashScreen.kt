package me.alberto.albertoventen.screens.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.alberto.albertoventen.R
import me.alberto.albertoventen.activity.MainActivity
import me.alberto.albertoventen.databinding.SplashScreenBinding
import me.alberto.albertoventen.util.FileExist
import me.alberto.albertoventen.util.Loading
import me.alberto.albertoventen.util.LoadingDone
import me.alberto.albertoventen.util.LoadingError
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding

    private val viewModel: SplashScreenViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)


        observe()

    }


    private fun observe() {
        viewModel.status.observe(this, Observer {
            it ?: return@Observer
            when (it) {
                is LoadingError -> Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                is LoadingDone -> {
                    gotoFilterScreen()
                }
                is FileExist -> {
                    binding.prgressBar.visibility = View.GONE
                    gotoFilterScreen()
                }
                is Loading -> {
                    if (it.progress > 0) {
                        binding.prgressBar.progress = it.progress.toInt()
                    }
                }
            }
        })
    }

    private fun gotoFilterScreen() {
        Timer().schedule(2000) {
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }
    }


}
