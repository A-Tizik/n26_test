package com.n26.test.myapplication.features.chart

import com.n26.test.myapplication.ChartsApiFake
import com.n26.test.myapplication.FakeApiError
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class BlockchainRepositoryTest {

    lateinit var chartsApi:ChartsApiFake
    @Before
    fun setUp() {
        chartsApi = ChartsApiFake()
    }

    @Test
    fun getMarketPrice() {
        val subscriber = TestSubscriber<MarketPrice>()

        chartsApi.success = true
        BlockchainRepository(chartsApi)
            .getMarketPrice()
            .toFlowable()
            .subscribe(subscriber)

        subscriber.assertValue { it.list.size == 1 && it.unit == "USD" }
        subscriber.assertComplete()

    }

    @Test
    fun `getMarketPrice returns FakeApiError on error`() {
        val subscriber = TestSubscriber<MarketPrice>()

        chartsApi.success = false
        BlockchainRepository(chartsApi)
            .getMarketPrice()
            .toFlowable()
            .subscribe(subscriber)

        subscriber.assertError { it is FakeApiError}

    }

}