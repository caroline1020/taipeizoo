package com.caroline.taipeizoo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.caroline.taipeizoo.database.ZooDatabase
import com.caroline.taipeizoo.database.asDomainModel
import com.caroline.taipeizoo.model.Plant
import com.caroline.taipeizoo.network.NetworkPlantContainer
import com.caroline.taipeizoo.network.ZooApi
import com.caroline.taipeizoo.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by nini on 2021/1/20.
 */
class PlantsRepository(private val database: ZooDatabase) {

    val plants: LiveData<List<Plant>> =
        Transformations.map(database.plantDao.getPlants()) {
            it.asDomainModel()
        }

    suspend fun refreshPlants() {
        withContext(Dispatchers.IO) {
            val response = ZooApi.retrofitService.getPlants()
            database.plantDao.insertAll(NetworkPlantContainer(response).asDatabaseModel())
        }
    }
}