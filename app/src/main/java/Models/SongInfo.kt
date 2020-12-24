package Models


data class SongInfo(val trackId: String, val trackName: String, val artistName: String,
                    val artworkUrl30: String, val artworkUrl60: String, val artworkUrl100: String,
                    val trackPrice: String, val collectionName: String, val trackTimeMillis: String,
                    val releaseDate: String, val primaryGenreName: String, val trackExplicitness: String,
                    val artistViewUrl: String, val collectionViewUrl: String, val trackViewUrl: String) {
}