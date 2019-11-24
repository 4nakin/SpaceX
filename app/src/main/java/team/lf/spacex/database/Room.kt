package team.lf.spacex.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Dao
interface SpaceXDao{

}

@Database(entities = [Launch::class], version = 1)
abstract class SpaceXDatabase():RoomDatabase(){
    abstract val SpaceXDao:SpaceXDao
}

private lateinit var INSTANCE : SpaceXDatabase

fun getDatabase(context: Context): SpaceXDatabase{
    synchronized(SpaceXDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SpaceXDatabase::class.java,
                "searches").build()
        }
    }
    return INSTANCE
}
