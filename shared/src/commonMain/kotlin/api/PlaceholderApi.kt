package api

import PlaceholderModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import ktor.createHttpClient

object PlaceholderApi {

    private val httpClient by lazy {
        createHttpClient()
    }

    suspend fun getPlaceholderModels() = httpClient.safeRequest<List<PlaceholderModel>> {
        accept(ContentType.Application.Json)
        url("http://192.168.31.65:8080/models")
    }.orEmpty()

    private suspend inline fun <reified T> HttpClient.safeRequest(
        block: HttpRequestBuilder.() -> Unit,
    ): T? = runCatching {
        val response = request { block() }
        if (response.status == HttpStatusCode.OK) {
            response.body<T>()
        } else {
            null
        }
    }.getOrNull()


}