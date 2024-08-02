package com.legalist.cytpto.di

import com.legalist.cytpto.repository.CyrptoDowland
import com.legalist.cytpto.repository.CyrptoIMPL
import com.legalist.cytpto.sevice.CyrptoApi
import com.legalist.cytpto.viewmodel.CyrptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<CyrptoApi> {
        val baseUrl = "https://raw.githubusercontent.com/"
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CyrptoApi::class.java)
    }

    single<CyrptoDowland> {
        CyrptoIMPL(get())
    }

    viewModel {
        CyrptoViewModel(get())
    }
}
