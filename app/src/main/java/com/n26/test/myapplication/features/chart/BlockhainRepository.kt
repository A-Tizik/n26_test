package com.n26.test.myapplication.features.chart

import com.n26.test.myapplication.network.models.MarketPriceResponse
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockchainRepository @Inject constructor(private val chartsApi: ChartsApi) {
    fun getMarketPrice() = chartsApi
        .gerMarketPrice()
        .map { it.toDomain() }
        .observeOn(Schedulers.computation())
}

private fun MarketPriceResponse.toDomain(): MarketPrice =
    MarketPrice(
        unit = unit,
        list = values.map {
            MarketPrice.Coordinates(it.x, it.y)
        }
    )

data class MarketPrice(
    val unit: String,
    val list: List<Coordinates>
) {
    data class Coordinates(
        val x: Int,
        val y: Float
    )
}