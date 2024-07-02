package example.com.modules.dapr

import example.com.modules.todos.dto.ConfigValue
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object MyHttpClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}

class Dapr{
    suspend fun retrieveAll(): Map<String, ConfigValue> {
        val response = MyHttpClient.client.get(" http://localhost:3500/v1.0/configuration/config-store")
        val data = Json.decodeFromString<Map<String,ConfigValue>>(response.bodyAsText())
        return data
    }
}