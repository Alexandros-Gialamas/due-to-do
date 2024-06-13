package com.alexandros.p.gialamas.duetodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable

@Database(version = 1, exportSchema = false, entities = [
        TaskTable::class,
        TaskCategoryTable::class,
])
abstract class Database : RoomDatabase() {

    abstract fun taskDao() : TaskDao
    abstract fun taskCategoryDao() : TaskCategoryDao

}