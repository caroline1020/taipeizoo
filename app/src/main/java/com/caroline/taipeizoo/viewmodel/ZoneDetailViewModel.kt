package com.caroline.taipeizoo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.caroline.taipeizoo.database.getDatabase
import com.caroline.taipeizoo.repository.PlantsRepository
import kotlinx.coroutines.launch

/**
 * Created by nini on 2021/1/18.
 */
class ZoneDetailViewModel(application: Application) :
    AndroidViewModel(application) {

    private val plantRepository = PlantsRepository(getDatabase(application))
    val plantList = plantRepository.plants
    private val _loading = MutableLiveData<LoadingState>()


    val loadingState: LiveData<LoadingState>
        get() = _loading

    fun refreshPlants() {
        if (_loading.value == LoadingState.LOADING) {
            return
        }
        viewModelScope.launch {
            _loading.value = LoadingState.LOADING
            try {
                plantRepository.refreshPlants()
                _loading.value = LoadingState.PENDING

            } catch (e: Exception) {
                _loading.value = LoadingState.ERROR
                Log.e(TAG, e.toString())
            }
        }
    }


    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ZoneDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ZoneDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    companion object {
        private const val TAG = "ZoneDetailViewModel"
    }
}