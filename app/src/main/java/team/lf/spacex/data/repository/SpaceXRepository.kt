package team.lf.spacex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.lf.spacex.data.database.SpaceXDatabase
import team.lf.spacex.data.database.asDomainLaunchModel
import team.lf.spacex.data.database.asDomainModels
import team.lf.spacex.data.database.entity.CompanyInfo
import team.lf.spacex.data.database.entity.HistoryEvent
import team.lf.spacex.data.network.SpaceXApiService
import team.lf.spacex.data.network.asDatabaseModels
import team.lf.spacex.data.ui_models.Launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class SpaceXRepository @Inject constructor(
    private val database: SpaceXDatabase,
    private val service: SpaceXApiService
) : BaseRepository() {

    val companyInfo: LiveData<CompanyInfo> =
        database.spaceXDao.getCompanyInfo()

    val allLaunches: LiveData<List<Launch>> =
        Transformations.map(database.spaceXDao.getAllLaunches()) {
            it.asDomainModels()
        }

    val historyEvents: LiveData<List<HistoryEvent>> = database.spaceXDao.getHistoryEvents()

    suspend fun loadAllLaunches(){
        withContext(Dispatchers.IO){
            safeApiCall(
                call = { service.getAllLaunchesAsync().await() },
                errorMessage = "Error fetching launches"
            )?.let {
                database.spaceXDao.clearLaunches()
                database.spaceXDao.insertAllLaunches(it.asDatabaseModels())
            }
        }
    }

    suspend fun loadPastLaunches(){
        withContext(Dispatchers.IO){
            safeApiCall(
                call = { service.getPastLaunchesAsync().await() },
                errorMessage = "Error fetching launches"
            )?.let {
                database.spaceXDao.clearLaunches()
                database.spaceXDao.insertAllLaunches(it.asDatabaseModels())
            }
        }
    }

    suspend fun loadUpcomingLaunches(){
        withContext(Dispatchers.IO){
            safeApiCall(
                call = { service.getUpcomingLaunchesAsync().await() },
                errorMessage = "Error fetching launches"
            )?.let {
                database.spaceXDao.clearLaunches()
                database.spaceXDao.insertAllLaunches(it.asDatabaseModels())
            }
        }
    }

    suspend fun loadLatestLaunch(){
        withContext(Dispatchers.IO){
            safeApiCall(
                call = { service.getLatestLaunchAsync().await() },
                errorMessage = "Error fetching launches"
            )?.let {
                database.spaceXDao.clearLaunches()
                database.spaceXDao.insertAllLaunches(listOf(it).asDatabaseModels())
            }
        }
    }

    suspend fun loadNextLaunch(){
        withContext(Dispatchers.IO){
            safeApiCall(
                call = { service.getNextLaunchAsync().await() },
                errorMessage = "Error fetching launches"
            )?.let {
                database.spaceXDao.clearLaunches()
                database.spaceXDao.insertAllLaunches(listOf(it).asDatabaseModels())
            }
        }
    }

    fun getLaunchByFlightNumberFromDatabase(flightNumber: String)
            : LiveData<Launch> {
        return Transformations.map(database.spaceXDao.getLaunchByFlightNumber(flightNumber)) {
            it.asDomainLaunchModel()
        }
    }

    suspend fun refreshCompanyInfo() {
        withContext(Dispatchers.IO) {
            val companyInfo = safeApiCall(
                call = { service.getCompanyInfoAsync().await() },
                errorMessage = "Error fetching companyInfo"
            )
            companyInfo?.let {
                database.spaceXDao.insertCompanyInfo(it)
            }
        }
    }

    suspend fun refreshHistory() {
        withContext(Dispatchers.IO) {
            val events = safeApiCall(
                call = { service.getLHistoryEventsAsync().await() },
                errorMessage = "Error fetching historyEvents"
            )
            events?.let {
                database.spaceXDao.insertHistoryEvent(it)
            }
        }
    }


}