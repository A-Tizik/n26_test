package com.n26.test.myapplication.features.chart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.n26.test.myapplication.ChartsApiFake
import com.n26.test.myapplication.Resource
import com.n26.test.myapplication.network.models.MarketPriceResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ChartViewModelTest {

    lateinit var chartsApi: ChartsApi

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        chartsApi = ChartsApiFake()
    }

    @Test
    fun `onChartRequested places Resource_Success in chart livedata`() {
        //Given
        val viewModel = ChartViewModel(BlockchainRepository(chartsApi))

        //When
        val testObserver = viewModel.chart.test()
        viewModel.onChartRequested()

        //Then
        testObserver.assertValue { it is Resource.Success }
    }
}