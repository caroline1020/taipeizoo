package com.caroline.taipeizoo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.caroline.taipeizoo.database.getDatabase
import com.caroline.taipeizoo.repository.ZonesRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by nini on 2021/1/18.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val zonesRepository = ZonesRepository(getDatabase(application))
    val zoneList = zonesRepository.zones
    private val _loadingState = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    fun refreshZones() {
        if (_loadingState.value == LoadingState.LOADING) {
            return
        }
        _loadingState.value = LoadingState.LOADING
        viewModelScope.launch {
            try {
                zonesRepository.refreshZones()
                _loadingState.value = LoadingState.PENDING
            } catch (networkError: IOException) {
                _loadingState.value = LoadingState.ERROR
                Log.e(TAG, networkError.toString())
            }
        }
    }

    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}