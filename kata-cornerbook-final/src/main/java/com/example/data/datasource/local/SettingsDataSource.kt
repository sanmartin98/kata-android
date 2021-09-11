package com.example.data.datasource.local

import kotlinx.coroutines.flow.StateFlow

interface SettingsDataSource {
    fun isLocationReporterLive(): StateFlow<Boolean>
    fun activateLocationReporter()
    fun deactivateLocationReporter()
}