package com.kangmicin.dilirik

import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import java.io.*

class Loader {
    private val musics: ArrayList<Music> = ArrayList()

    companion object{
        val instance = Loader()
    }

    fun loadMusicLibrary(input: InputStream, force: Boolean = false): ArrayList<Music> {
        val klaxon = Klaxon()
        if (musics.isEmpty() || force) {
            val data = loadData(input)
            musics.clear()
            JsonReader(StringReader(data)).use { reader ->
                reader.beginArray {
                    while (reader.hasNext()) {
                        klaxon.parse<Music>(reader)?.let { musics.add(it) }
                    }
                }
            }
        }

        return musics
    }

    private fun loadData(input: InputStream): String {
        val builder: StringBuilder = StringBuilder()
        input.use {
            val reader: Reader = BufferedReader(InputStreamReader(it, "UTF-8"))
            for(line in reader.readLines()) {
                builder.append(line)
            }
        }

        return builder.toString()
    }
}