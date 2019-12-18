package team.lf.spacex.data.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface SpaceXApiService {
    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/"
    }

    @GET("launches")
    fun getLaunchesAsync(): Deferred<List<Launch>>
}

