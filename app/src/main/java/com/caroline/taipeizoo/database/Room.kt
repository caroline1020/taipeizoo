package com.caroline.taipeizoo.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by nini on 2021/1/20.
 */

@Dao
interface ZoneDao {
    @Query("select * from databasezone")
    fun getZones(): LiveData<List<DatabaseZone>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<DatabaseZone>)
}

@Dao
interface PlantDao {

    @Query("select * from databaseplant")
    fun getPlants(): LiveData<List<DatabasePlant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<DatabasePlant>)
}

@Database(entities = [DatabaseZone::class, DatabasePlant::class], version = 1)
abstract class ZooDatabase : RoomDatabase() {
    abstract val zoneDao: ZoneDao
    abstract val plantDao: PlantDao
}


private lateinit var INSTANCE: ZooDatabase

fun getDatabase(context: Context): ZooDatabase {
    synchronized(ZooDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ZooDatabase::class.java,
                "Zoo"
            ).build()
        }
    }
    return INSTANCE
}
