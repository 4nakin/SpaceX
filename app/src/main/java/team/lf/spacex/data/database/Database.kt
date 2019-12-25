package team.lf.spacex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import team.lf.spacex.data.database.entity.CompanyInfo
import team.lf.spacex.data.database.entity.HistoryEvent
import team.lf.spacex.data.database.entity.Launch

/**
 * The Room Database.
 **/
@Database(entities = [Launch::class, CompanyInfo::class, HistoryEvent::class], version = 1)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val spaceXDao: SpaceXDao
}