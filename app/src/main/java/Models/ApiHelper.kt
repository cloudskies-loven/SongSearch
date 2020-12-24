package Models

import Controller.SongDetailBuilder.apiClient
import Interfaces.ApiClient

class ApiHelper(private val apiService: ApiClient) {
    suspend fun getSongDetail(id: Int) = apiService.getSongDetail(id)
    suspend fun getSong(term: String) = apiService.getSong(term)
}