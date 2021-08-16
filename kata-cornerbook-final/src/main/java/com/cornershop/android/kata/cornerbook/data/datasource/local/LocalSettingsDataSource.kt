package com.cornershop.android.kata.cornerbook.data.datasource.local

import android.content.SharedPreferences
import com.cornershop.android.kata.cornerbook.data.datasource.local.LocalSettingsDataSource.Key.PREFERENCE_LOCATION_REPORTER
import com.cornershop.android.kata.cornerbook.data.datasource.local.PreferenceConstants.PREFERENCE_LOCATION_REPORTER_NEW
import com.cornershop.android.kata.cornerbook.data.datasource.local.PreferenceConstants.PREFERENCE_LOCATION_REPORTER_OLD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalSettingsDataSource(
        sharedPreferences: SharedPreferences
) : BasePreferences(sharedPref = sharedPreferences), SettingsDataSource {

    enum class Key {
        PREFERENCE_LOCATION_REPORTER
    }

    private val locationReporterMode: Int
        get() = getInt(PREFERENCE_LOCATION_REPORTER, PREFERENCE_LOCATION_REPORTER_OLD)

    var isLocationReporter: Boolean = false
        get() = locationReporterMode == PREFERENCE_LOCATION_REPORTER_NEW
        set(value) {
            save(PREFERENCE_LOCATION_REPORTER, if (value) {
                PREFERENCE_LOCATION_REPORTER_NEW
            } else {
                PREFERENCE_LOCATION_REPORTER_OLD
            })
            field = value
        }

    private val _isLocationReporterLive: MutableStateFlow<Boolean> = MutableStateFlow(isLocationReporter)

    private val preferenceChangedListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                when (key) {
                    PREFERENCE_LOCATION_REPORTER.name -> {
                        _isLocationReporterLive.value = isLocationReporter
                    }
                }
            }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

    override fun isLocationReporterLive(): StateFlow<Boolean> = _isLocationReporterLive.asStateFlow()

    override fun activateLocationReporter() {
        isLocationReporter = true
    }

    override fun deactivateLocationReporter() {
        isLocationReporter = false
    }
}