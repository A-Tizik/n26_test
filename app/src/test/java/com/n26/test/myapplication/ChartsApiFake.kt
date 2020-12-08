package com.n26.test.myapplication

import com.n26.test.myapplication.features.chart.ChartsApi
import com.n26.test.myapplication.network.models.MarketPriceResponse
import io.reactivex.Single

class ChartsApiFake : ChartsApi {

    var success: Boolean = true

    override fun gerMarketPrice(): Single<MarketPriceResponse> =
            Single.just(
                MarketPriceResponse(
                    "ok",
                    "Market Price (USD)",
                    "USD",
                    "day",
                    "Average USD market price across major bitcoin exchanges.",
                    listOf(MarketPriceResponse.Coordinates(1, 1f))
                )
            ).doOnSubscribe {
                if (!success) throw FakeApiError()
            }

}

class FakeApiError:Throwable()


