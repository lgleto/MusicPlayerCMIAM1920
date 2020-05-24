package com.example.musicplayer.ui.songdetail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplayer.R
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.fragment_song_detail.*


private const val ARG_SONG_NAME = "song_name"
private const val ARG_ARTIST    = "artist"
private const val ARG_URL       = "url"

class SongDetailFragment : Fragment() {

    private var songName: String? = null
    private var artist: String? = null
    private var urlString: String? = null

    private var permissionToPlay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            songName = it.getString(ARG_SONG_NAME)
            artist = it.getString(ARG_ARTIST)
            urlString = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewArtist.text = artist
        textViewSong.text = songName

        buttonPlay.setOnClickListener {

            val simpleExoplayer : SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context!!, DefaultTrackSelector(),  DefaultLoadControl() )
            val uri: Uri = Uri.parse(urlString)
            val dataSourceFactory: com.google.android.exoplayer2.upstream.DataSource.Factory = com.google.android.exoplayer2.upstream.DefaultDataSourceFactory( context, urlString )
            val audioSource: MediaSource =  ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
            simpleExoplayer.prepare(audioSource,true, true)
            simpleExoplayer.setPlayWhenReady(true);
        }

        if (permissionToPlay) {
            buttonPlay.visibility = View.VISIBLE
        }else {
            buttonPlay.visibility = View.GONE
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(songName: String, artist: String, url : String) =
            SongDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SONG_NAME, songName)
                    putString(ARG_ARTIST, artist)
                    putString(ARG_URL, url)
                }
            }
    }
}
