package com.example.packagist.environment

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual object LocalAppLocale {

    private var defaultLocale: Locale? = null

    actual val current: String
        @Composable
        get() = Locale.getDefault().toLanguageTag()

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current

        if (defaultLocale == null) {
            defaultLocale = Locale.getDefault()
        }

        val newLocale = value?.let {
            Locale.forLanguageTag(it)
        } ?: defaultLocale!!

        Locale.setDefault(newLocale)

        val newConfiguration = Configuration(configuration)
        newConfiguration.setLocale(newLocale)

        val resources = LocalContext.current.resources
        resources.updateConfiguration(
            newConfiguration,
            resources.displayMetrics
        )

        return LocalConfiguration.provides(newConfiguration)
    }
}