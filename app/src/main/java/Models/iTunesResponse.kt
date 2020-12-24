package Models

class ITunesResponse(val results: List<Song>) {
}

class ITunesResponseDetail(val results: List<SongInfo>)