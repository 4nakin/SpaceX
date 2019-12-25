package team.lf.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import team.lf.spacex.data.database.entity.CompanyInfo
import team.lf.spacex.data.database.entity.HistoryEvent
import team.lf.spacex.data.database.entity.Launch


/**
 * Data Access Object for the launches table.
 */
@Dao
interface SpaceXDao {
    @Query("select * from launches")
    fun getAllLaunches(): LiveData<List<Launch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLaunches(launches: List<Launch>)

    @Query("select * from launches where flight_number = :flightNumber")
    fun getLaunchByFlightNumber(flightNumber: String): LiveData<Launch>

    @Query("select * from launches where upcoming = :isUpcoming")
    fun getUpcomingLaunches(isUpcoming: Boolean): LiveData<List<Launch>>

    @Query("select * from companyinfo")
    fun getCompanyInfo(): LiveData<CompanyInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompanyInfo(companyInfo: CompanyInfo)

    @Query("select * from historyevent")
    fun getHistoryEvents(): LiveData<List<HistoryEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryEvent(historyEvents: List<HistoryEvent>)

    @Query("DELETE FROM launches")
    fun clearLaunches()

}






