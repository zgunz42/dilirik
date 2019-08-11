package com.kangmicin.dilirik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class LyricBottomSheetFragment(private val lyric: String) : BottomSheetDialogFragment() {
    override fun getTheme(): Int {
        return R.style.BottomSheetTheme
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.bottomsheet_lyric, container, false)
        val lyricView = view.findViewById<TextView>(R.id.music_lyric)

        lyricView.text = lyric

        return view
    }



}