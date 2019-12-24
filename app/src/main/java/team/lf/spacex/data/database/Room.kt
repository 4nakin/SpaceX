package team.lf.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import team.lf.spacex.ui.company_info.data.CompanyInfo
import team.lf.spacex.ui.company_info.data.HistoryEvent


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

/**
 * The Room Database that contains the Lunch table.
 **/
@Database(entities = [Launch::class, CompanyInfo::class, HistoryEvent::class], version = 1)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val spaceXDao: SpaceXDao
}




