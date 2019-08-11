package com.kangmicin.dilirik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.title = getString(R.string.about_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
