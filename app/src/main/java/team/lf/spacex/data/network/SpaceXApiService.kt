package team.lf.spacex.data.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import team.lf.spacex.ui.company_info.data.CompanyInfo
import team.lf.spacex.ui.company_info.data.HistoryEvent

/**
 * SpaceX REST API access points
 */
interface SpaceXApiService {
    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/"
    }

    @GET("launches")
    fun getAllLaunchesAsync(): Deferred<Response<List<Launch>>>

    @GET("launches/past")
    fun getPastLaunchesAsync(@Query("order") order: String = "desc"): Deferred<Response<List<Launch>>>

    @GET("launches/upcoming")
    fun getUpcomingLaunchesAsync(): Deferred<Response<List<Launch>>>

    @GET("launches/latest")
    fun getLatestLaunchAsync(): Deferred<Response<Launch>>

    @GET("launches/next")
    fun getNextLaunchAsync(): Deferred<Response<Launch>>

    @GET("info")
    fun getCompanyInfoAsync(): Deferred<Response<CompanyInfo>>

    @GET("history")
    fun getLHistoryEventsAsync(): Deferred<Response<List<HistoryEvent>>>
}

