package team.lf.spacex.data.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
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
    fun getLaunchesAsync(): Deferred<Response<List<Launch>>>

    @GET("info")
    fun getCompanyInfoAsync(): Deferred<Response<CompanyInfo>>

    @GET("history")
    fun getLHistoryEventsAsync(): Deferred<Response<List<HistoryEvent>>>
}

