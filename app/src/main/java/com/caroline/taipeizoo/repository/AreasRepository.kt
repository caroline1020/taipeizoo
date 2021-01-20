package com.caroline.taipeizoo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.caroline.taipeizoo.database.AreasDatabase
import com.caroline.taipeizoo.database.asDomainModel
import com.caroline.taipeizoo.model.NetworkArea
import com.caroline.taipeizoo.network.NetworkAreaContainer
import com.caroline.taipeizoo.network.ZooApi
import com.caroline.taipeizoo.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by nini on 2021/1/20.
 */
class AreasRepository(private val database: AreasDatabase) {

    val areas: LiveData<List<NetworkArea>> = Transformations.map(database.areaDao.getAreas()) {
        it.asDomainModel()
    }

    suspend fun refreshAreas() {
        withContext(Dispatchers.IO) {
            val response = ZooApi.retrofitService.getAreas()
            database.areaDao.insertAll(NetworkAreaContainer(response).asDatabaseModel())
        }
    }
}