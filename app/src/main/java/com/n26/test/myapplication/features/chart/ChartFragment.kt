package com.n26.test.myapplication.features.chart

import android.os.Bundle
import android.view.View
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.n26.test.myapplication.R
import com.n26.test.myapplication.Resource
import com.n26.test.myapplication.databinding.FragmentChartBinding
import com.n26.test.myapplication.view.DateValueFormatter
import com.n26.test.myapplication.view.displayState
import com.n26.test.myapplication.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChartFragment : Fragment(R.layout.fragment_chart) {
    private val binding by viewBinding(FragmentChartBinding::bind)
    private val viewModel: ChartViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chart.observe(viewLifecycleOwner) { resource->

            binding.root.displayState(resource)

            with(binding.chart.xAxis) {
                valueFormatter = DateValueFormatter()
                granularity = 1f
                isGranularityEnabled = true
                position = XAxis.XAxisPosition.BOTTOM
            }
            binding.chart.description = Description().apply {
                text = getString(R.string.chart_description)
            }

            when(resource) {
                is Resource.Success -> {
                    val dataSet = resource.data.coordinates
                        .map { Entry(it.x.toFloat(), it.y) }
                        .let { LineDataSet(it,resource.data.unit) }


                    binding.chart.data = LineData(dataSet)
                }
                is Resource.Loading -> {
                    //nothing to do here
                }
                is Resource.Error -> {
                    binding.retry.setOnClickListener {
                        viewModel.onChartRequested()
                    }
                }
            }


        }
    }
}


