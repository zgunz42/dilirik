package com.kangmicin.dilirik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {
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
    }
}
