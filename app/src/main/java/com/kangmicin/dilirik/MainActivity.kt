package com.kangmicin.dilirik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Hello World"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun toastText(view: android.view.View) {
        val toa: Toast = Toast.makeText(view.context, "Hi You There", Toast.LENGTH_SHORT)

        toa.setGravity(Gravity.TOP, 0, 0)
        toa.show()
    }
}
