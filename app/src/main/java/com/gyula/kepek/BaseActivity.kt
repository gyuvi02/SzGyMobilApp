package com.gyula.kepek

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

internal const val LEKERDEZES = "LEKERDEZES"
internal const val KEPATVITEL = "KEPATVITEL"

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    internal fun activateToolbar(enableHome: Boolean) {

        var toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}