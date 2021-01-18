package com.caroline.taipeizoo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.taipeizoo.model.Plant
import com.caroline.taipeizoo.network.ZooApi
import kotlinx.coroutines.launch

/**
 * Created by nini on 2021/1/18.
 */
class AreaViewModel : ViewModel() {


    private val _loading = MutableLiveData<Boolean>()
    private val _filteredPlant = MutableLiveData<List<Plant>>()

    val loading: LiveData<Boolean>
        get() = _loading

    val filteredPlant: LiveData<List<Plant>>
        get() = _filteredPlant

    fun filterPlant(areaName: String) {

        viewModelScope.launch {
            _loading.value = true
            val filter = ZooApi.retrofitService.getPlants().result.results.filter { it ->
                it.F_Location.contains(areaName)
            }
            val set=HashSet(filter)
            _filteredPlant.value = ArrayList(set)
            _loading.value = false
        }
    }
}