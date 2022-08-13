package com.example.passwordsaver.viewmodel

import androidx.lifecycle.*
import com.example.passwordsaver.database.SaverEntity
import com.example.passwordsaver.repository.SaverRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AllListViewModel(private val repo: SaverRepo): ViewModel() {

    var allData: LiveData<List<SaverEntity>> = repo.allData.asLiveData()

    fun delete(saverEntity: SaverEntity){
        viewModelScope.launch {
            repo.delete(saverEntity)
        }
    }

    fun update(saverEntity: SaverEntity){
        viewModelScope.launch {
            repo.update(saverEntity)
        }
    }

}
class AllListViewModelFactory(private val repo: SaverRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AllListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}