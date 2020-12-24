package Interfaces

import Models.ITunesResponse
import Models.Song
import Models.ITunesResponseDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("search")
    suspend fun getSong(
        @Query("term") term: String,
        @Query("country") country: String? = "PH",
        @Query("media") media: String? = "music",
        @Query("limit") limit: Int? = 60): ITunesResponse
    @GET("lookup")
    suspend fun getSongDetail(@Query("id") id: Int): ITunesResponseDetail
}