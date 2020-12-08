package com.n26.test.myapplication.view

import android.widget.ViewFlipper
import com.n26.test.myapplication.Resource

fun ViewFlipper.displayState(resource: Resource<*>) {
    displayedChild = when(resource) {
        is Resource.Success -> 0
        is Resource.Loading -> 1
        is Resource.Error -> 2
    }
}