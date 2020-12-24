package Controller

import Interfaces.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SongDetailBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiClient: ApiClient = getRetrofit().create(ApiClient::class.java)
}