package com.n26.test.myapplication.features.chart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.n26.test.myapplication.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ChartViewModel @ViewModelInject constructor(private val blockchainRepository: BlockchainRepository): ViewModel() {
    private val disposable = CompositeDisposable()
    private val chartSubject: BehaviorSubject<Resource<MarketPriceModel>> = BehaviorSubject.create()

    val chart = chartSubject.toFlowable(BackpressureStrategy.LATEST).throttleLatest(500,TimeUnit.MILLISECONDS).toLiveData()

    init { onChartRequested() }

    fun onChartRequested() {
        disposable += blockchainRepository
            .getMarketPrice()
            .map { it.toMarketPriceModel() }
            .doOnSubscribe { chartSubject.onNext(Resource.Loading(chartSubject.value?.data)) }
            .subscribeBy(
                onError = {
                    chartSubject.onNext(Resource.Error(chartSubject.value?.data,"An error has occurred"))
                },
                onSuccess = {
                    chartSubject.onNext(Resource.Success(it))
                })
    }

    override fun onCleared() {
        disposable.dispose()
    }


}

data class MarketPriceModel(
    val unit: String,
    val coordinates: List<Coordinates>
) {
    data class Coordinates(
        val x: Int,
        val y: Float
    )
}

fun MarketPrice.toMarketPriceModel() = MarketPriceModel(
    unit = unit,
    coordinates = list.map {
        MarketPriceModel.Coordinates(it.x, it.y)
    }
)
