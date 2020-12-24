package Models

import androidx.annotation.Keep

@Keep
class Song(trackId: Int, trackName: String, artistName: String,
           artworkUrl30: String, artworkUrl60: String, artworkUrl100: String,
           trackPrice: String) {

    var trackId: Int = 0
    var trackName: String = ""
    var artistName: String = ""
    var artworkUrl30: String = ""
    var artworkUrl60: String = ""
    var artworkUrl100: String = ""
    var trackPrice: String = "0"

}