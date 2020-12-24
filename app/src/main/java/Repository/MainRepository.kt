package Repository

import Models.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getSongDetails(id: Int) = apiHelper.getSongDetail(id)
    suspend fun getSong(term: String) = apiHelper.getSong(term)
}