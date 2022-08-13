package com.example.passwordsaver

import android.app.Application
import com.example.passwordsaver.database.SaverRoom
import com.example.passwordsaver.repository.SaverRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DatabaseApplication: Application() {

    val application = CoroutineScope(SupervisorJob())

    val database: SaverRoom by lazy{ SaverRoom.getDatabase(this) }
    val repo: SaverRepo by lazy{ SaverRepo(database.getDao()) }
}