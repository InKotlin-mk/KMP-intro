package ktor

import HTTP_TIMEOUT_SECONDS
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
    }
    config(this)
}