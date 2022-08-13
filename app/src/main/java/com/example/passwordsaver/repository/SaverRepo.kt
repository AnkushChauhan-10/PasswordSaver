package com.example.passwordsaver.repository

import androidx.annotation.WorkerThread
import com.example.passwordsaver.database.SaverDao
import com.example.passwordsaver.database.SaverEntity
import kotlinx.coroutines.flow.Flow

class SaverRepo(private val saverDao: SaverDao) {

    val allData: Flow<List<SaverEntity>> = saverDao.getAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(saverEntity: SaverEntity){
        saverDao.insert(saverEntity)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(saverEntity: SaverEntity){
        saverDao.delete(saverEntity)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getData(appName: String):SaverEntity{
        return saverDao.getData(appName)
    }

    suspend fun update(saverEntity: SaverEntity){
        saverDao.update(saverEntity)
    }
}