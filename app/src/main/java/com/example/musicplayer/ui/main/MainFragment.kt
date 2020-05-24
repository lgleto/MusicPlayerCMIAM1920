package com.example.musicplayer.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.musicplayer.R
import com.example.musicplayer.Song
import com.example.musicplayer.ui.songdetail.SongDetailFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    var songs : MutableList<Song> = ArrayList<Song>()

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        var rootView =  inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        songs.clear()
        songs.add(Song("Shine you crazy daimond", "Pink Floyd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"))
        songs.add(Song("Led Zepplin", "Stair way to haven", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"))


        listViewSongs.adapter = SongsAdatper()
    }

    inner class SongsAdatper : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = layoutInflater.inflate(R.layout.row_song,  parent, false)

            val textViewName = view.findViewById<TextView>(R.id.textViewSongName)
            val textViewArtist = view.findViewById<TextView>(R.id.textViewArtist)

            textViewName.text = songs[position].name
            textViewArtist.text = songs[position].artist

            view.setOnClickListener {
                val songFragment =  SongDetailFragment.newInstance( songs[position].name,songs[position].artist, songs[position].url)
                fragmentManager?.beginTransaction()?.replace(R.id.container,songFragment)?.addToBackStack(null)?.commit()
            }

            return view
        }

        override fun getItem(position: Int): Any {
            return songs[position]
        }

        override fun getItemId(position: Int): Long {
           return 0
        }

        override fun getCount(): Int {
            return songs.size
        }

    }

}
