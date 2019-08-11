package com.kangmicin.dilirik

import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        val instance = Util()
    }

    fun displayDate(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        var formatter = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault())

        return formatter.format(parser.parse(date))
    }
}