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
import team.lf.spacex.ui.launches.LaunchesFilterType
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

    val allLaunches: LiveData<List<Launch>> =
        Transformations.map(database.spaceXDao.getAllLaunches()) {
            it.asDomainModels()
        }

    suspend fun loadLaunches(currentFilterType: LaunchesFilterType) {
        withContext(Dispatchers.IO) {

            /**
             * I've decided to clear db and use different api call instead of using room query("select * from launches where ...")
             * Perhaps it is not good for app speed
             * */
            val launches = mutableListOf<team.lf.spacex.data.network.Launch>().apply {
                when (currentFilterType) {
                    LaunchesFilterType.PAST_LAUNCHES -> {
                        safeApiCall(
                            call = { service.getPastLaunchesAsync().await() },
                            errorMessage = "Error fetching launches"
                        )?.let {
                            addAll(it)
                        }
                    }
                    LaunchesFilterType.UPCOMMING_LAUNCHES -> {
                        safeApiCall(
                            call = { service.getUpcomingLaunchesAsync().await() },
                            errorMessage = "Error fetching launches"
                        )?.let {
                            addAll(it)
                        }
                    }
                    LaunchesFilterType.LATEST_LAUNCH -> {
                        safeApiCall(
                            call = { service.getLatestLaunchAsync().await() },
                            errorMessage = "Error fetching launches"
                        )?.let {
                            add(it)
                        }
                    }
                    LaunchesFilterType.NEXT_LAUNCH -> {
                        safeApiCall(
                            call = { service.getNextLaunchAsync().await() },
                            errorMessage = "Error fetching launches"
                        )?.let {
                            add(it)
                        }
                    }
                    LaunchesFilterType.ALL_LAUNCHES -> {
                        safeApiCall(
                            call = { service.getAllLaunchesAsync().await() },
                            errorMessage = "Error fetching launches"
                        )?.let {
                            addAll(it)
                        }
                    }
                }
            }
            if (launches.size > 0) database.spaceXDao.clearLaunches()
            database.spaceXDao.insertAllLaunches(launches.asDatabaseModels())
        }
    }


    fun getLaunchByFlightNumberFromDatabase(flightNumber: String)
            : LiveData<Launch> {
        return Transformations.map(database.spaceXDao.getLaunchByFlightNumber(flightNumber)) {
            it.asDomainLaunchModel()
        }
    }

    val companyInfo: LiveData<CompanyInfo> = database.spaceXDao.getCompanyInfo()

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

    val historyEvents: LiveData<List<HistoryEvent>> = database.spaceXDao.getHistoryEvents()

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