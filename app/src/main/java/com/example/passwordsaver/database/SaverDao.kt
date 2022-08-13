package com.example.passwordsaver.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SaverDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SaverEntity)

    @Update
    suspend fun update(entity: SaverEntity)

    @Delete
    suspend fun delete(entity: SaverEntity)


    @Query("Select * from data_Table")
    fun getAllData(): Flow<List<SaverEntity>>

    @Query("Select * from data_Table where app_name = :appName")
    suspend fun getData(appName: String?): SaverEntity


}