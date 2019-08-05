package com.kangmicin.dilirik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.*

class DetailActivity : AppCompatActivity() {
    private var musics: ArrayList<Music> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var music: Music = intent?.extras?.getSerializable(MainActivity.PICK_MUSIC) as Music
        var thumbnail: ImageView = findViewById(R.id.music_thumbnail)
        var genre: TextView = findViewById(R.id.music_genre)
        var title: TextView = findViewById(R.id.music_title)
        var artis: TextView = findViewById(R.id.music_artist)
        var release: TextView = findViewById(R.id.music_release)
        var producer: TextView = findViewById(R.id.music_producer)
        var description: TextView = findViewById(R.id.music_description)
        var recomended: RecyclerView = findViewById(R.id.music_recomendation)

        Glide.with(this.baseContext)
            .load(music.thumbnail)
            .apply(RequestOptions.overrideOf(255, 255))
            .into(thumbnail)

        genre.text = music.genre
        title.text = music.title
        artis.text = music.artist
        release.text = music.release
        producer.text = music.producer.joinToString()
        description.text = music.description

        streamMusicData(loadData())?.let { musics.addAll(it) }

        recomended.adapter = ListMusicAdapter(musics, Display.CARD) { openDetailPage(it) }

        recomended.hasFixedSize()
    }

    private fun openDetailPage(music: Music) {
        val detailPage = Intent(this@DetailActivity, DetailActivity::class.java)
        detailPage.putExtra(MainActivity.PICK_MUSIC, music)

        startActivity(detailPage)
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
}
