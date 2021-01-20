package com.caroline.taipeizoo.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by nini on 2021/1/20.
 */

@Dao
interface AreaDao {
    @Query("select * from databasearea")
    fun getAreas(): LiveData<List<DatabaseArea>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<DatabaseArea>)
}

@Dao
interface PlantDao {

    @Query("select * from databaseplant")
    fun getPlants(): LiveData<List<DatabasePlant>>

//    @Query("select * from databaseplant")
//    fun getPlantsByLocation(location: String): LiveData<List<DatabasePlant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<DatabasePlant>)
}

@Database(entities = [DatabaseArea::class, DatabasePlant::class], version = 1)
abstract class ZooDatabase : RoomDatabase() {
    abstract val areaDao: AreaDao
    abstract val plantDao: PlantDao
}


private lateinit var INSTANCE: ZooDatabase

fun getDatabase(context: Context): ZooDatabase {
    synchronized(ZooDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ZooDatabase::class.java,
                "areas"
            ).build()
        }
    }
    return INSTANCE
}
