package team.lf.spacex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.lf.spacex.database.SpaceXDatabase
import team.lf.spacex.database.asDomainModels
import team.lf.spacex.domain.Launch
import team.lf.spacex.network.SpaceXApi
import team.lf.spacex.network.SpaceXApiService
import team.lf.spacex.network.asDatabaseModels

class SpaceXRepository(private val database: SpaceXDatabase){


    val allLaunches : LiveData<List<Launch>> =
        Transformations.map(database.SpaceXDao.getAllLaunches()){
            it.asDomainModels()
        }

    suspend fun refreshAllLaunches(){
        withContext(Dispatchers.IO){
            val allLaunches = SpaceXApi.retrofitService.getLaunchesAsync().await()
            database.SpaceXDao.insertAll(allLaunches.asDatabaseModels())
        }
    }


}