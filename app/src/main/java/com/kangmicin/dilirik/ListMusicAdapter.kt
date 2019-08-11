package com.kangmicin.dilirik

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions



class ListMusicAdapter(private var musicList: ArrayList<Music>, private var display: Display, private var listener: ((music: Music)->Unit)? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("type is", this.display.toString())

        return when(this.display) {
            Display.LIST -> {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lyric, parent, false)
                ListViewHolder(view)
            }

            Display.CARD -> {
                val view: View =  LayoutInflater.from(parent.context).inflate(R.layout.card_lyric, parent, false)
                CardViewHolder(view)
            }
        }


    }



    override fun getItemCount(): Int {
        return musicList.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var music: Music = musicList[position]
        val (title, thumbnail, genre, artist) = music

        if (this.display === Display.LIST) {
            var innerHolder = this.ListViewHolder(holder.itemView)
            Glide.with(holder.itemView.context)
                .load(thumbnail)
                .placeholder(R.drawable.img)
                .apply(RequestOptions.overrideOf(255, 255))
                .into(innerHolder.thumbnail)

            innerHolder.title.text = title
            innerHolder.genre.text = genre
            innerHolder.artist.text = artist

            innerHolder.itemView.setOnClickListener{
                listener?.invoke(music)
            }
        }

        if (this.display === Display.CARD) {
            var innerHolder = this.CardViewHolder(holder.itemView)

            Glide.with(holder.itemView.context)
                .load(thumbnail)
                .apply(RequestOptions.overrideOf(255, 255))
                .into(innerHolder.thumbnail)

            innerHolder.title.text = title

            innerHolder.itemView.setOnClickListener{
                listener?.invoke(music)
            }
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnail: ImageView = itemView.findViewById(R.id.music_thumbnail)
        var genre: TextView = itemView.findViewById(R.id.music_genre)
        var title: TextView = itemView.findViewById(R.id.music_title)
        var artist: TextView = itemView.findViewById(R.id.music_artist)
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnail: ImageView = itemView.findViewById(R.id.music_thumbnail)
        var title: TextView = itemView.findViewById(R.id.music_title)
    }
}