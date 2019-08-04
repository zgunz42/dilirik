package com.kangmicin.dilirik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var musicView: RecyclerView
    private var musics: ArrayList<Music> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Lyric List"

        musicView = findViewById(R.id.lyrics)

        streamMusicData(loadData())?.let { musics.addAll(it) }

        musicView.adapter = ListMusicAdapter(musics) { openDetailPage(it) }

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
        val klakon = Klaxon()
        val result = ArrayList<Music>()
        JsonReader(StringReader(raw)).use { reader ->
            reader.beginObject {
                when (reader.nextName()) {
                    "musics" -> {
                        for (music in reader.nextArray()) {
                            klakon.parse<Music>(reader)?.let {
                                result.add(it)
                            }
                        }
                    }
                }


            }
        }
        return result
    }

    private fun loadData(): String {
        val input: InputStream = resources.openRawResource(R.raw.lyrics)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        input.use {
            val reader: Reader = BufferedReader(InputStreamReader(it, "UTF-8"))
            var n: Int
            while (true) {
                n = reader.read(buffer)
                if (n == 1) {
                    break
                }
                writer.write(buffer, 0, n)
            }
        }

        return writer.toString()
    }

    private fun openAboutPage() {
        var openPage = Intent(this@MainActivity, AboutActivity::class.java)
        startActivity(openPage)
    }

    private fun openDetailPage(music: Music) {
        var detailPage = Intent(this@MainActivity, DetailActivity::class.java)
        detailPage.putExtra(PICK_MUSIC, music)

        startActivity(detailPage)
    }

    companion object {
        const val PICK_MUSIC = "pick_music"
    }
}
