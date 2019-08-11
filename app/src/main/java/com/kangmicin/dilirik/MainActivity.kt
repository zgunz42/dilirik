package com.kangmicin.dilirik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var musicView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Lyric List"

        setupMusicList()
    }

    private fun setupMusicList() {
        musicView = findViewById(R.id.lyrics)

        musicView.adapter = ListMusicAdapter(loadListData(), Display.LIST) { openDetailPage(it) }

        musicView.hasFixedSize()
    }

    private fun loadListData(): ArrayList<Music> {
        val data = resources.openRawResource(R.raw.lyrics)

        return Loader.instance.loadMusicLibrary(data)
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
