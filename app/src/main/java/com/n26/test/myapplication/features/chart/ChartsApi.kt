package com.n26.test.myapplication.features.chart

import com.n26.test.myapplication.network.models.MarketPriceResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ChartsApi {
    @GET("market-price?timespan=1year&rollingAverage=8hours&format=json")
    fun gerMarketPrice(): Single<MarketPriceResponse>
}