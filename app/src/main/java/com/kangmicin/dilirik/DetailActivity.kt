package com.kangmicin.dilirik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {
    private lateinit var lyricBottomSheetFragment: LyricBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupActionBar()

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

        lyricBottomSheetFragment = LyricBottomSheetFragment(music.lyric)

        recommended.adapter = ListMusicAdapter(loadListData(), Display.CARD) { openDetailPage(it) }

        recommended.hasFixedSize()
    }

    private fun setupActionBar() {
        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openDetailPage(music: Music) {
        val detailPage = Intent(this@DetailActivity, DetailActivity::class.java)
        detailPage.putExtra(MainActivity.PICK_MUSIC, music)

        startActivity(detailPage)
    }


    private fun loadListData(): ArrayList<Music> {
        val data = resources.openRawResource(R.raw.lyrics)

        return Loader.instance.loadMusicLibrary(data)
    }

    fun showLyric(view: View) {
        if (supportFragmentManager !== null) {
            lyricBottomSheetFragment.show(supportFragmentManager, "show_lyric")
        }
    }
}
