package data.model

enum class KeyValueStorageKeys {
    IS_ON_BOARDED,
    IS_USER_LOGGED_IN,
    DO_REMEMBER,
    USER_OBJ;

    val key get() = this.name
}