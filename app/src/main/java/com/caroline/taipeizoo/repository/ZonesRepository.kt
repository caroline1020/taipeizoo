package com.caroline.taipeizoo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.caroline.taipeizoo.database.ZooDatabase
import com.caroline.taipeizoo.database.asZoneDomainModel
import com.caroline.taipeizoo.model.Zone
import com.caroline.taipeizoo.network.NetworkAreaContainer
import com.caroline.taipeizoo.network.ZooApi
import com.caroline.taipeizoo.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by nini on 2021/1/20.
 */
class ZonesRepository(private val database: ZooDatabase) {

    val zones: LiveData<List<Zone>> = Transformations.map(database.zoneDao.getZones()) {
        it.asZoneDomainModel()
    }

    suspend fun refreshZones() {
        withContext(Dispatchers.IO) {
            val response = ZooApi.retrofitService.getZones()
            database.zoneDao.insertAll(NetworkAreaContainer(response).asDatabaseModel())
        }
    }
}