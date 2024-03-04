package mk.inkotlin.kmp.intro

import PlaceholderModel
import SERVER_PORT
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    contentNegotiation()
    routing {
        get("/models") {
            call.respond(HttpStatusCode.OK, jsonResource())
        }
    }
}

fun jsonResource(): List<PlaceholderModel> =
    Application::class.java.classLoader.getResource("internal/placeholder.json")
        ?.readText()?.convertToDataClass<List<PlaceholderModel>>().orEmpty()

private val json = Json {
    ignoreUnknownKeys = true
}

internal inline fun <reified R : Any> String.convertToDataClass() =
    json.decodeFromString<R>(this)

private fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
    install(DefaultHeaders){}
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
    }
}