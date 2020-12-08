package com.n26.test.myapplication.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.n26.test.myapplication.features.chart.ChartsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideChartsApi(): ChartsApi {
        val jsonConverter = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(MediaType.get("application/json"))

        return Retrofit.Builder()
            .baseUrl("https://api.blockchain.info/charts/")
            .addConverterFactory(jsonConverter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ChartsApi::class.java)
    }

}