package com.kangmicin.dilirik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListMusicAdapter(private var musicList: ArrayList<Music>, private var listerner: ((music: Music)->Unit)? = null) :
    RecyclerView.Adapter<ListMusicAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lyric, parent, false)

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var music: Music = musicList[position]
        val (title, thumbnail, genre) = music

        Glide.with(holder.itemView.context)
            .load(thumbnail)
            .apply(RequestOptions.overrideOf(255, 255))
            .into(holder.thumbnail)

        holder.title.text = title
        holder.genre.text = genre

        holder.itemView.setOnClickListener{
            listerner?.invoke(music)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnail: ImageView = itemView.findViewById(R.id.music_thumbnail)
        var genre: TextView = itemView.findViewById(R.id.music_genre)
        var title: TextView = itemView.findViewById(R.id.music_title)
    }
}