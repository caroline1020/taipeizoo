package com.caroline.taipeizoo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.taipeizoo.model.Area
import com.caroline.taipeizoo.model.Plant
import com.caroline.taipeizoo.network.ZooApi
import kotlinx.coroutines.launch

/**
 * Created by nini on 2021/1/18.
 */
class MainViewModel : ViewModel() {


    private val _loading = MutableLiveData<LoadingState>()
    private val _data = MutableLiveData<List<Area>>()
    private val _selectedArea = MutableLiveData<Area>()
    private val _selectedPlant = MutableLiveData<Plant>()

    val loading: LiveData<LoadingState>
        get() = _loading

    val data: LiveData<List<Area>>
        get() = _data
    val selectedArea: LiveData<Area>
        get() = _selectedArea
    val selectedPlant: LiveData<Plant>
        get() = _selectedPlant

    fun loadIntroduction() {
        if(_data.value!=null&& _data.value!!.isNotEmpty()){
            return
        }
        viewModelScope.launch {
            _loading.value = LoadingState.LOADING
            try {
                _data.value = ZooApi.retrofitService.getAreas().result.results
                _loading.value = LoadingState.PENDING
            } catch (e: Exception) {
                _data.value = ArrayList()
                _loading.value = LoadingState.ERROR
                Log.e(Companion.TAG, e.toString())

            }
        }

    }

    fun selectArea(area: Area?) {

        _selectedArea.value = area
    }

    fun selectedPlant(plant: Plant) {

        _selectedPlant.value = plant
    }


    companion object {
        private const val TAG = "MainViewModel"
    }

}