package team.lf.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import team.lf.spacex.ui.company_info.data.CompanyInfo


/**
 * Data Access Object for the launches table.
 */
@Dao
interface SpaceXDao {
    @Query("select * from launches")
    fun getAllLaunches(): LiveData<List<Launch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launches: List<Launch>)

    @Query("select * from launches where flight_number = :flightNumber")
    fun getLaunchByFlightNumber(flightNumber: String): LiveData<Launch>

    @Query("select * from companyinfo")
    fun getCompanyInfo(): LiveData<CompanyInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompanyInfo(companyInfo: CompanyInfo)

}

/**
 * The Room Database that contains the Lunch table.
 **/
@Database(entities = [Launch::class, CompanyInfo::class], version = 1)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val spaceXDao: SpaceXDao
}




