package team.lf.spacex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.lf.spacex.data.database.SpaceXDatabase
import team.lf.spacex.data.database.asDomainLaunchModel
import team.lf.spacex.data.database.asDomainModels
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.data.network.SpaceXApi
import team.lf.spacex.data.network.SpaceXApiService
import team.lf.spacex.data.network.asDatabaseModels

class SpaceXRepository(
    private val database: SpaceXDatabase,
    private val service: SpaceXApiService
) {

    val allLaunches: LiveData<List<Launch>> =
        Transformations.map(database.SpaceXDao.getAllLaunches()) {
            it.asDomainModels()
        }


    suspend fun refreshAllLaunches() {
        withContext(Dispatchers.IO) {
            val allLaunches = service.getLaunchesAsync().await()
            database.SpaceXDao.insertAll(allLaunches.asDatabaseModels())
        }
    }

    fun getLaunchByFlightNumberFromDatabase(flightNumber: String)
            : LiveData<Launch> {
        return Transformations.map(database.SpaceXDao.getLaunchByFlightNumber(flightNumber)) {
            it.asDomainLaunchModel()
        }
    }

}