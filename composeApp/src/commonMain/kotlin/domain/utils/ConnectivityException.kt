package domain.utils

const val ErrorMessageNoConnectivity = "Connectivity lost.\nPlease check your network."

class ConnectivityException : Exception {
    var serverHtml: String? = null

    constructor(message: String?) : super(message) {}
    constructor() : super(ErrorMessageNoConnectivity) {}
    constructor(
        message: String?,
        _serverHtml: String?
    ) : super(message ?: ErrorMessageNoConnectivity) {
        serverHtml = _serverHtml
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}