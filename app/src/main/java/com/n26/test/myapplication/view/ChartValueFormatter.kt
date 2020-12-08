package com.n26.test.myapplication.view

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats seconds since epoch to "MMM" pattern in default locale
 */
class DateValueFormatter: ValueFormatter() {
    private val simpleFormat = SimpleDateFormat("MMM", Locale.getDefault())
    override fun getFormattedValue(value: Float): String {
        return simpleFormat.format(Date(value.toLong() * 1000))
    }
}