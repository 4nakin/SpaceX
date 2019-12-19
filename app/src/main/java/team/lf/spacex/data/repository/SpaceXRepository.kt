package team.lf.spacex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.lf.spacex.data.database.SpaceXDatabase
import team.lf.spacex.data.database.asDomainLaunchModel
import team.lf.spacex.data.database.asDomainModels
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.data.network.SpaceXApiService
import team.lf.spacex.data.network.asDatabaseModels
import team.lf.spacex.ui.company_info.data.CompanyInfo
import team.lf.spacex.ui.company_info.data.HistoryEvent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class SpaceXRepository @Inject constructor(
    private val database: SpaceXDatabase,
    private val service: SpaceXApiService
):BaseRepository() {

    val allLaunches: LiveData<List<Launch>> =
        Transformations.map(database.spaceXDao.getAllLaunches()) {
            it.asDomainModels()
        }

    suspend fun refreshAllLaunches() {
        withContext(Dispatchers.IO) {
            val launchesResponse = safeApiCall(
                call = {service.getLaunchesAsync().await()},
                errorMessage = "Error fetching launches"
            )
            launchesResponse?.let {
                database.spaceXDao.insertAll(it.asDatabaseModels())
            }
        }
    }

    fun getLaunchByFlightNumberFromDatabase(flightNumber: String)
            : LiveData<Launch> {
        return Transformations.map(database.spaceXDao.getLaunchByFlightNumber(flightNumber)) {
            it.asDomainLaunchModel()
        }
    }

    val companyInfo: LiveData<CompanyInfo> = database.spaceXDao.getCompanyInfo()

    suspend fun refreshCompanyInfo(){
        withContext(Dispatchers.IO){
            val companyInfo = safeApiCall(
                call = {service.getCompanyInfoAsync().await()},
                errorMessage = "Error fetching companyInfo"
            )
            companyInfo?.let {
                database.spaceXDao.insertCompanyInfo(it)
            }
        }
    }

    val historyEvents: LiveData<List<HistoryEvent>> = database.spaceXDao.getHistoryEvents()

    suspend fun refreshHistory(){
        withContext(Dispatchers.IO){
            val historyEvents = safeApiCall(
                call = {service.getLHistoryEventsAsync().await()},
                errorMessage = "Error fetching historyEvents"
            )
            historyEvents?.let {
                database.spaceXDao.insertHistoryEvent(it)
            }
        }
    }





}