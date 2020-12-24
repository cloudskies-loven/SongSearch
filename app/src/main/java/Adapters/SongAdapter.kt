package Adapters


import Models.Song
import Holders.SongViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class SongAdapter(private var songs: List<Song>): RecyclerView.Adapter<SongViewHolder>(), Filterable {

    var songFilterList = ArrayList<Song>()

    init {
        songFilterList = songs as ArrayList<Song>
    }
    override fun getItemCount(): Int {
        return songs.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.song_item, parent, false)
        return SongViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val results = songs[position]
        holder.itemView.findViewById<TextView>(R.id.song_title).text = results.trackName
        holder.itemView.findViewById<TextView>(R.id.song_artist).text = results.artistName
        holder.itemView.findViewById<TextView>(R.id.price).text = holder.itemView.context.getString(R.string.price_string, results.trackPrice.toString())
        val trackImgThumbnail = holder.itemView.findViewById<ImageView>(R.id.song_img)
        Picasso.with(holder?.itemView?.context).load(results.artworkUrl60).into(trackImgThumbnail)

        holder?.songInfo = results
    }



    fun setSongs(songs: List<Song>) {
        var listSong = this.songs as ArrayList<Song>
        listSong.apply {
            clear()
            addAll(songs)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    songFilterList = songs as ArrayList<Song>
                } else {
                    val resultList = ArrayList<Song>()
                    songFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = songFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                songFilterList = results?.values as ArrayList<Song>
                notifyDataSetChanged()
            }

        }
    }

}