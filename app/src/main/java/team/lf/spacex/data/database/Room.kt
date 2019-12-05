package team.lf.spacex.data.database

import android.content.Context
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
abstract class SpaceXDatabase():RoomDatabase(){
    abstract val SpaceXDao: SpaceXDao
}

private lateinit var INSTANCE : SpaceXDatabase

fun getDatabase(context: Context): SpaceXDatabase {
    synchronized(SpaceXDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SpaceXDatabase::class.java,
                "searches").build()
        }
    }
    return INSTANCE
}
