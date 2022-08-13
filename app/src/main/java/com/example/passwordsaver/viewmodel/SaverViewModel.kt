package com.example.passwordsaver.viewmodel

import androidx.lifecycle.*
import com.example.passwordsaver.database.SaverDao
import com.example.passwordsaver.database.SaverEntity
import com.example.passwordsaver.repository.SaverRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SaverViewModel(private val repo: SaverRepo):ViewModel() {

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String, dateTime: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount,dateTime)
        insertItem(newItem)
    }

    private fun insertItem(item: SaverEntity) {
        viewModelScope.launch {
            repo.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String,dateTime : String): SaverEntity {
        return SaverEntity(itemName,itemPrice,itemCount,dateTime)
    }

}

class SaverViewModelFactory(private val repo: SaverRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaverViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SaverViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}