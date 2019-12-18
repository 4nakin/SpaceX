package team.lf.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SpaceXDao{
    @Query("select * from launches")
    fun getAllLaunches():LiveData<List<Launch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launches: List<Launch>)

    @Query("select * from launches where flight_number = :flightNumber")
    fun getLaunchByFlightNumber(flightNumber:String): LiveData<Launch>

}

@Database(entities = [Launch::class], version = 1)
abstract class SpaceXDatabase:RoomDatabase(){
    abstract val spaceXDao: SpaceXDao
}




