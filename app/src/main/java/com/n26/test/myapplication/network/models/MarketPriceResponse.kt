package com.n26.test.myapplication.network.models

import kotlinx.serialization.Serializable
import java.time.chrono.ChronoPeriod

@Serializable
data class MarketPriceResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<Coordinates>
) {
    @Serializable
    data class Coordinates(
        val x: Int,
        val y: Float
    )
}