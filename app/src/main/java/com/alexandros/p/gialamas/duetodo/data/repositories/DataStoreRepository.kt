package com.alexandros.p.gialamas.duetodo.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.util.Constants.PREFERENCE_CATEGORY_KEY
import com.alexandros.p.gialamas.duetodo.util.Constants.PREFERENCE_DATE_KEY
import com.alexandros.p.gialamas.duetodo.util.Constants.PREFERENCE_PRIORITY_KEY
import com.alexandros.p.gialamas.duetodo.util.Constants.PREFERENCE_NAME
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.SelectedCategoryState
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject;


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val prioritySortStateKey = stringPreferencesKey(name = PREFERENCE_PRIORITY_KEY)
        val dateSortStateKey = stringPreferencesKey(name = PREFERENCE_DATE_KEY)
        val categoryStateKey = stringPreferencesKey(name = PREFERENCE_CATEGORY_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistPrioritySortState(taskPriority: TaskPriority) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.prioritySortStateKey] = taskPriority.name
        }
    }

    suspend fun persistDateSortState(dateSortOrder : DateSortOrder) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.dateSortStateKey] = dateSortOrder.name
        }
    }

    suspend fun persistCategoryState(category : String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.categoryStateKey] = category
        }
    }

    val readPrioritySortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val prioritySortState = preferences[PreferenceKeys.prioritySortStateKey] ?: TaskPriority.NONE.name
            prioritySortState
        }

    val readDateSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val dateSortState = preferences[PreferenceKeys.dateSortStateKey] ?: DateSortOrder.ASCENDING.name
            dateSortState
        }

    val readCategoryState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val categoryState = preferences[PreferenceKeys.categoryStateKey] ?: SelectedCategoryState.NONE.categoryName
            categoryState
        }

}


