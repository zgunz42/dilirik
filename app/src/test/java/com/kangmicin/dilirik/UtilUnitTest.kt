package com.kangmicin.dilirik

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilUnitTest {
    @Test
    fun convert_isoDateString() {
        val date = "2015-08-09T05:36:07+0000"
        val formatted = Util.instance.displayDate(date)

        assertEquals(formatted, "August, 09 2015")
    }
}
