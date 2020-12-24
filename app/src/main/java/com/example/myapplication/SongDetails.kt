package com.example.myapplication


import Controller.SongDetailBuilder
import Holders.SongViewHolder
import Models.*
import Models.SongInfo
import Utility.Status
import ViewModelFactory.SongViewModelFactory
import ViewModels.SongViewModel
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class  SongDetails : AppCompatActivity() {

    private lateinit var songViewModel: SongViewModel
    private lateinit var artistName: TextView
    private lateinit var songName: TextView
    private lateinit var collectionName: TextView
    private lateinit var trackPrice: TextView
    private lateinit var trackTime: TextView
    private lateinit var releasedDate: TextView
    private lateinit var genre: TextView
    private lateinit var explicitness: TextView
    private lateinit var trackImg: ImageView
    private lateinit var mCardLayout: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_details)

        initUi()
        initViewModel()
        initObserver()
    }

    private fun initUi() {
        songName = findViewById(R.id.song_detail_name);
        artistName = findViewById(R.id.song_detail_artist);
        collectionName = findViewById(R.id.song_detail_collection_name);
        trackPrice = findViewById(R.id.song_detail_track_price);
        trackTime = findViewById(R.id.song_detail_track_time);
        releasedDate = findViewById(R.id.song_detail_release_date);
        genre = findViewById(R.id.song_detail_genre);
        explicitness = findViewById(R.id.song_detail_explicitness);
        trackImg = findViewById(R.id.song_detail_img)
        mCardLayout = findViewById(R.id.card_detail_layout)
    }

    private fun initObserver() {
        val trackId = intent.getIntExtra(SongViewHolder.TRACK_ID, 0)
        songViewModel.getSongDetails(id = trackId).observe(this, Observer {
            it?.let { resource ->
                when (resource.status ) {
                    Status.SUCCESS -> {
                        mCardLayout.visibility = View.VISIBLE
                        resource.data?.let { songDetails -> retrieveValues(songDetails.results) }
                    }
                    Status.ERROR -> {
                        mCardLayout.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        mCardLayout.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun initViewModel() {
        songViewModel = ViewModelProviders.of(this,
        SongViewModelFactory(ApiHelper(SongDetailBuilder.apiClient))
        )
            .get(SongViewModel::class.java)
    }

    private fun retrieveValues(songDetails: List<SongInfo>) {
       val songData = songDetails[0]
       artistName.text = getString(R.string.artist_info, songData.artistName)
       songName.text = getString(R.string.track_info, songData.trackName)
       collectionName.text = getString(R.string.collection_info, songData.collectionName)
       trackPrice.text = getString(R.string.price_string, songData.trackPrice)
       trackTime.text = String.format("%2d:%2d",
           TimeUnit.MILLISECONDS.toMinutes(songData.trackTimeMillis.toLong()) % TimeUnit.HOURS.toMinutes(1),
           TimeUnit.MILLISECONDS.toSeconds(songData.trackTimeMillis.toLong()) % TimeUnit.HOURS.toMinutes(1))
       val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
       val formatter = SimpleDateFormat("MMMM dd, y", Locale.getDefault())
       releasedDate.text = formatter.format(parser.parse(songData.releaseDate)).toString()
       genre.text = songData.primaryGenreName

       if (songData.trackExplicitness == "notExplicit") {
           explicitness.setBackgroundResource(R.color.general)
           explicitness.text = getString(R.string.not_explicit)
       } else {
           explicitness.setBackgroundResource(R.color.error)
           explicitness.text = getString(R.string.explicit)
       }

       Picasso.with(this).load(songData.artworkUrl100).into(trackImg)

       artistName.setOnClickListener {
           val browserIntent = Intent(Intent.ACTION_VIEW)
           browserIntent.data = Uri.parse(songData.artistViewUrl)
           startActivity(browserIntent)
       }

        songName.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(songData.trackViewUrl)
            startActivity(browserIntent)
        }

        collectionName.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(songData.collectionViewUrl)
            startActivity(browserIntent)
        }
    }
}