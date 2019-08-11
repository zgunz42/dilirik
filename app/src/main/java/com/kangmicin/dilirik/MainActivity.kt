package com.kangmicin.dilirik

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var musicView: RecyclerView
    private var musics: ArrayList<Music> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Lyric List"

        musicView = findViewById(R.id.lyrics)

        streamMusicData(loadData())?.let { musics.addAll(it) }

        musicView.adapter = ListMusicAdapter(musics, Display.LIST) { openDetailPage(it) }

        musicView.hasFixedSize()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_about -> {
                openAboutPage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun streamMusicData(raw: String): ArrayList<Music>? {
        val klaxon = Klaxon()
        val result = ArrayList<Music>()
        JsonReader(StringReader(raw)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    klaxon.parse<Music>(reader)?.let { result.add(it) }
                }
            }
        }
        return result
    }

    private fun loadData(): String {
        val input: InputStream = resources.openRawResource(R.raw.lyrics)
        val builder: StringBuilder = StringBuilder()
        input.use {
            val reader: Reader = BufferedReader(InputStreamReader(it, "UTF-8"))
            for(line in reader.readLines()) {
                builder.append(line)
            }
        }

        return builder.toString()
    }

    private fun openAboutPage() {
        val openPage = Intent(this@MainActivity, AboutActivity::class.java)
        startActivity(openPage)
    }

    private fun openDetailPage(music: Music) {
        val detailPage = Intent(this@MainActivity, DetailActivity::class.java)
        detailPage.putExtra(PICK_MUSIC, music)

        startActivity(detailPage)
    }

    companion object {
        const val PICK_MUSIC = "pick_music"
    }
}
