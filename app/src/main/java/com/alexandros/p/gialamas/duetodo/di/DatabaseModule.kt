package com.alexandros.p.gialamas.duetodo.di

import android.content.Context
import androidx.room.Room
import com.alexandros.p.gialamas.duetodo.data.Database
import com.alexandros.p.gialamas.duetodo.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context : Context
    ) = Room.databaseBuilder(context, Database::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideTaskDao(database: Database) = database.taskDao()

    @Singleton
    @Provides
    fun provideTaskCategoryDao(database: Database) = database.taskCategoryDao()

//    @Singleton
//    @Provides
//    fun provideTaskReminderDao(database: Database) = database.taskReminderDao()

//    @Singleton
//    @Provides
//    fun provideTaskWithCategoryDao(database: Database) = database.taskWithCategoryDao()


}