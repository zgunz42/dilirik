package com.kangmicin.dilirik

import java.io.Serializable
import java.util.*

data class Music(
    var title: String = "",
    var thumbnail: String = "",
    var genre: String = "",
    var description: String = "",
    var lyric: String = "",
    var artist: String = "",
    var producer: Array<String> = emptyArray(),
    var release: String = ""
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Music

        if (title != other.title) return false
        if (genre != other.genre) return false
        if (lyric != other.lyric) return false
        if (description != other.description) return false
        if (thumbnail != other.thumbnail) return false
        if (artist != other.artist) return false
        if (!producer.contentEquals(other.producer)) return false
        if (release != other.release) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + thumbnail.hashCode()
        result = 31 * result + lyric.hashCode()
        result = 31 * result + artist.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + producer.contentHashCode()
        result = 31 * result + release.hashCode()
        return result
    }
}