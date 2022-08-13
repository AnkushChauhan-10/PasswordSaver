package com.example.passwordsaver.viewmodel

import androidx.lifecycle.*
import com.example.passwordsaver.database.SaverEntity
import com.example.passwordsaver.repository.SaverRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainScreenViewModel(private val repo: SaverRepo): ViewModel() {

    private val _data = MutableLiveData<SaverEntity>()
    val data : LiveData<SaverEntity> = _data

    fun getData(appName: String) {
        viewModelScope.launch {
            _data.value = repo.getData(appName)
        }
    }
    fun delete(saverEntity: SaverEntity){
        viewModelScope.launch {
            repo.delete(saverEntity)
        }
    }

}

class MainScreenViewModelFactory(private val repo: SaverRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}