package com.kangmicin.dilirik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {
    private var musics: ArrayList<Music> = arrayListOf()

    private lateinit var lyricBottomSheetFragment: LyricBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val music: Music = intent?.extras?.getSerializable(MainActivity.PICK_MUSIC) as Music
        val thumbnail: ImageView = findViewById(R.id.music_thumbnail)
        val genre: TextView = findViewById(R.id.music_genre)
        val title: TextView = findViewById(R.id.music_title)
        val artist: TextView = findViewById(R.id.music_artist)
        val release: TextView = findViewById(R.id.music_release)
        val producer: TextView = findViewById(R.id.music_producer)
        val description: TextView = findViewById(R.id.music_description)
        val recommended: RecyclerView = findViewById(R.id.music_recomendation)

        Glide.with(this.baseContext)
            .load(music.thumbnail)
            .placeholder(R.drawable.img)
            .apply(RequestOptions.overrideOf(255, 255))
            .into(thumbnail)

        genre.text = music.genre
        title.text = music.title
        artist.text = music.artist
        description.text = music.description
        producer.text = music.producer.joinToString()
        release.text = Util.instance.displayDate(music.release)

        streamMusicData(loadData())?.let { musics.addAll(it) }

        supportActionBar?.title = getString(R.string.detail_title)

        lyricBottomSheetFragment = LyricBottomSheetFragment(music.lyric)

        recommended.adapter = ListMusicAdapter(musics, Display.CARD) { openDetailPage(it) }

        recommended.hasFixedSize()
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

    fun showLyric(view: View) {
        if (supportFragmentManager !== null) {
            lyricBottomSheetFragment.show(supportFragmentManager, "show_lyric")
        }
    }
}
