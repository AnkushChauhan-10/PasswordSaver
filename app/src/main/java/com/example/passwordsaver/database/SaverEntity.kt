package com.example.passwordsaver.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
class SaverEntity(@PrimaryKey@NonNull @ColumnInfo(name = "app_name")val name:String,
                  @ColumnInfo(name = "user_name")val userName:String,
                  @ColumnInfo(name = "password")val password:String,
                  @ColumnInfo(name = "date")val date:String)
