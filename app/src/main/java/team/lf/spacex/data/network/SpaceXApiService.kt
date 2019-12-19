package team.lf.spacex.data.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import team.lf.spacex.ui.company_info.data.CompanyInfo

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
    fun getCompanyInfo():Deferred<Response<CompanyInfo>>
}

