package data.repository.cache

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import data.model.KeyValueStorageKeys
import data.model.User
import kotlinx.serialization.ExperimentalSerializationApi

class KeyValueStorageRepoImpl(private val settings: Settings) : KeyValueStorageRepo {

    override var isOnboarded: Boolean
        get() = settings.getBoolean(KeyValueStorageKeys.IS_ON_BOARDED.key, false)
        set(value) {
            settings[KeyValueStorageKeys.IS_ON_BOARDED.key] = value
        }

    override var isUserLoggedIn: Boolean
        get() = settings.getBoolean(KeyValueStorageKeys.IS_USER_LOGGED_IN.key, false)
        set(value) {
            settings[KeyValueStorageKeys.IS_USER_LOGGED_IN.key] = value
        }

    override var doRemember: Boolean
        get() = settings.getBoolean(KeyValueStorageKeys.DO_REMEMBER.key, false)
        set(value) {
            settings[KeyValueStorageKeys.DO_REMEMBER.key] = value
        }
    override var accessToken: String?
        get() = settings.getStringOrNull(KeyValueStorageKeys.ACCESS_TOKEN.key)
        set(value) {
            settings[KeyValueStorageKeys.ACCESS_TOKEN.key] = value
        }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override var user: User?
        get() = settings.decodeValueOrNull(User.serializer(), KeyValueStorageKeys.USER_OBJ.key)
        set(value) {
            value?.let {
                settings.encodeValue(
                    User.serializer(),
                    KeyValueStorageKeys.USER_OBJ.key,
                    it
                )
            }
        }
}