package com.rxmuhammadyoussef.anabeesh.ui

import android.content.Context
import android.content.Intent

interface ActivityArgs {

    fun intent(activity: Context): Intent

    fun launch(activity: Context) = activity.startActivity(intent(activity))
}