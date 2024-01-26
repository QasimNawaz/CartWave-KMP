package di

import com.russhwolf.settings.Settings
import org.koin.dsl.module


val keyValueSettingsModule = module {
    single { Settings() }
}