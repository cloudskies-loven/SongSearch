package com.example.myapplication

import Adapters.LoadAdapter
import Adapters.SongAdapter
import Controller.SongDetailBuilder
import Models.*
import Utility.Status
import ViewModelFactory.SongViewModelFactory
import ViewModels.SongViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import koleton.Koleton
import koleton.SkeletonLoader


class MainActivity : AppCompatActivity() {
    private lateinit var songViewModel: SongViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: SongAdapter
    private lateinit var mLoadAdapter: LoadAdapter
    private lateinit var mErrorCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val skeletonLoader = SkeletonLoader.Builder(this)
            .cornerRadius(2F)
            .build()
        Koleton.setSkeletonLoader(skeletonLoader)

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this);

        mRecyclerView  = findViewById<RecyclerView>(R.id.recycler_view_main)
        val dividerItemDecoration = DividerItemDecoration(
            mRecyclerView.context,
            linearLayoutManager.orientation
        )
        mRecyclerView.addItemDecoration(dividerItemDecoration)
        mErrorCard = findViewById(R.id.error_card);
        val errorImage = findViewById<ImageView>(R.id.error_img);
        Picasso.with(this).load(getString(R.string.error_src)).into(errorImage)
        mRecyclerView.findViewById<RecyclerView>(R.id.recycler_view_main).setBackgroundColor(Color.LTGRAY)
        mAdapter = SongAdapter(arrayListOf())
        mLoadAdapter = LoadAdapter()
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = mAdapter
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.hint = "Search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // adapter.filter.filter(newText)
                newText?.let { initObserver(it) }

                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    private fun initViewModel() {
        songViewModel = ViewModelProviders.of(
            this,
            SongViewModelFactory(ApiHelper(SongDetailBuilder.apiClient))
        )
                .get(SongViewModel::class.java)
    }

    private fun initObserver(term: String) {
        songViewModel.getSong(term = term).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mErrorCard.visibility = View.GONE
                        mRecyclerView.adapter = mAdapter
                        resource.data?.let { songs ->
                            retrieveValues(songs.results)
                        }
                    }
                    Status.ERROR -> {
                        mRecyclerView.visibility = View.GONE
                        mErrorCard.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        mRecyclerView.visibility = View.VISIBLE
                        mRecyclerView.adapter = mLoadAdapter
                    }
                }
            }
        })
    }

    private fun retrieveValues(songs: List<Song>) {
        if (songs.count() <= 0) {
            mRecyclerView.visibility = View.GONE
            mErrorCard.visibility = View.VISIBLE
            Toast.makeText(this, "No Songs Available", Toast.LENGTH_LONG).show()
        }
        mAdapter.apply {
            setSongs(songs)
            notifyDataSetChanged()
        }
    }

}