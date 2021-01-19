package com.caroline.taipeizoo.viewmodel

import android.util.Log
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


    private val _loading = MutableLiveData<LoadingState>()
    private val _filteredPlant = MutableLiveData<List<Plant>>()

    val loadingState: LiveData<LoadingState>
        get() = _loading

    val filteredPlant: LiveData<List<Plant>>
        get() = _filteredPlant

    fun loadFilteredPlants(areaName: String) {
        if (_filteredPlant.value != null && _filteredPlant.value!!.isNotEmpty()) {
            return
        }
        viewModelScope.launch {

            _loading.value = LoadingState.LOADING
            try {
                val filter = ZooApi.retrofitService.getPlants().result.results.filter {
                    it.F_Location.contains(areaName)
                }
                //filter duplicate items
                val set = HashSet(filter)
                _filteredPlant.value = ArrayList(set)
                _loading.value = LoadingState.PENDING

            } catch (e: Exception) {
                _loading.value = LoadingState.ERROR
                Log.e(TAG, e.toString())
            }
        }
    }

    companion object {
        private const val TAG = "AreaViewModel"
    }
}