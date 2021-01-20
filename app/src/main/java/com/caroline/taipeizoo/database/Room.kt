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

@Database(entities = [DatabaseArea::class], version = 1)
abstract class AreasDatabase: RoomDatabase() {
    abstract val areaDao: AreaDao
}


private lateinit var INSTANCE: AreasDatabase

fun getDatabase(context: Context): AreasDatabase {
    synchronized(AreasDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AreasDatabase::class.java,
                "areas").build()
        }
    }
    return INSTANCE
}