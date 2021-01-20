package com.caroline.taipeizoo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.caroline.taipeizoo.database.getDatabase
import com.caroline.taipeizoo.repository.AreasRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by nini on 2021/1/18.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val areasRepository = AreasRepository(getDatabase(application))
    val areaList = areasRepository.areas
    private val _loading = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState>
        get() = _loading


    fun refreshAreas() {
        if (_loading.value == LoadingState.LOADING) {
            return
        }
        _loading.value = LoadingState.LOADING
        viewModelScope.launch {
            try {
                areasRepository.refreshAreas()
                _loading.value = LoadingState.PENDING
            } catch (networkError: IOException) {
                _loading.value = LoadingState.ERROR
                Log.e(Companion.TAG, networkError.toString())
            }
        }
    }

    fun loadAreas() {
        if (areaList.value == null || areaList.value!!.isEmpty()) {
            refreshAreas()
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