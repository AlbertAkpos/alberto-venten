package me.alberto.albertoventen.di

import me.alberto.albertoventen.screens.carowner.CarOwnerViewModel
import me.alberto.albertoventen.screens.filter.FilterViewModel
import me.alberto.albertoventen.screens.splash.SplashScreenViewModel
import me.alberto.albertoventen.util.helper.CSVFileHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {
    single { CSVFileHelper(androidContext()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { CarOwnerViewModel(get()) }
    viewModel { FilterViewModel() }
}