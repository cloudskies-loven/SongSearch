package Holders

import Models.Song
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.SongDetails

class SongViewHolder(view: View, var songInfo: Song? = null): RecyclerView.ViewHolder(view) {

    companion object {
        const val QUERY_TEXT = "DEFAULT_QUERY"
        const val TRACK_ID = "DEFAULT_TRACK_id"
    }
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, SongDetails::class.java)
            intent.putExtra(QUERY_TEXT, songInfo?.artistName)
            intent.putExtra(TRACK_ID, songInfo?.trackId)
            view.context.startActivity(intent)
        }
    }
}