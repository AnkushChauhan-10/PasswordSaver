package com.example.passwordsaver.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [SaverEntity::class], version = 1, exportSchema = false)
abstract class SaverRoom:RoomDatabase() {

    abstract fun getDao(): SaverDao

    companion object{
        @Volatile
        private var INSTANCE: SaverRoom? = null
        fun getDatabase(context: Context): SaverRoom{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SaverRoom::class.java,
                    "Saver Database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}