package com.caroline.taipeizoo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.taipeizoo.model.Info
import com.caroline.taipeizoo.network.ZooApi
import kotlinx.coroutines.launch

/**
 * Created by nini on 2021/1/18.
 */
class MainViewModel : ViewModel() {


    private val _loading = MutableLiveData<Boolean>()
    private val _data = MutableLiveData<List<Info>>()

    val loading: LiveData<Boolean>
        get() = _loading

    val data: LiveData<List<Info>>
        get() = _data


    fun loadIntroduction() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _data.value = ZooApi.retrofitService.getIntroduction().result.results
                _loading.value = false
            } catch (e: Exception) {
                _data.value = ArrayList()
                _loading.value = false

            }
        }

    }

}