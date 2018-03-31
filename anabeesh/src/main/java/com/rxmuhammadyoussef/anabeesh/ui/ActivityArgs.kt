package com.rxmuhammadyoussef.anabeesh.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface ActivityArgs {

    fun intent(activity: Context): Intent

    fun launch(activity: Context) = activity.startActivity(intent(activity))

    fun launch(activity: Context, bundle: Bundle) = activity.startActivity(intent(activity), bundle)
}