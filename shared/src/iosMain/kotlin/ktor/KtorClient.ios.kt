package ktor

import HTTP_TIMEOUT_SECONDS
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Darwin) {
    engine {
        configureRequest {
            setTimeoutInterval(HTTP_TIMEOUT_SECONDS.toDouble())
            setAllowsCellularAccess(true)
            setAllowsExpensiveNetworkAccess(true)
        }
    }
    config(this)
}