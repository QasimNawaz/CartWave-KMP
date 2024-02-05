package data.repository.cache

import data.model.User

interface KeyValueStorageRepo {
    var isOnboarded: Boolean
    var isUserLoggedIn: Boolean
    var doRemember: Boolean
    var accessToken: String?
    var user: User?
}