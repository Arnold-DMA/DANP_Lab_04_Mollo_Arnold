package com.danp.cursos_jpds_01

import android.graphics.Color
import android.graphics.fonts.FontFamily
import android.graphics.fonts.FontStyle
import android.webkit.WebSettings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class NotePrefs(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveNoteBackgroundColor(noteBackgroundColor: String) {
        dataStore.edit { preferences ->
            preferences[BACKGROUND_COLOR] = noteBackgroundColor
        }
    }
    suspend fun saveNoteFontStyle(noteFontStyle: String) {
        dataStore.edit { preferences ->
            preferences[STYLE] = noteFontStyle
        }
    }
    suspend fun saveNoteFontSize(noteFontSize: String) {
        dataStore.edit { preferences ->
            preferences[SIZE] = noteFontSize
        }
    }

    val backgroundColor: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[BACKGROUND_COLOR] ?: Color.CYAN.toString()
        }

    val fontStyle: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[STYLE] ?: "sans-serif"
        }

    val fontSize: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[SIZE] ?: "14.0f"
        }

    companion object {
        val PREFS_NAME = "PREFS_NAME"
        private val BACKGROUND_COLOR = stringPreferencesKey("key_app_background_color")
        private val STYLE = stringPreferencesKey("key_app_font_style")
        private val SIZE = stringPreferencesKey("key_app_font_size")
    }
}