package example.com

import com.mongodb.kotlin.client.coroutine.MongoClient
import example.com.modules.dapr.Dapr
import example.com.plugins.configureRouting
import example.com.utils.ObjectIdAsStringSerializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.bson.types.ObjectId
import redis.clients.jedis.Jedis

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            serializersModule = SerializersModule {
                contextual(ObjectId::class, ObjectIdAsStringSerializer)
            }
        })
    }

    runBlocking {
        val dapr = Dapr().retrieveAll()
        val mongo = MongoClient.create(dapr["mongoUri"]?.value.toString())
        val db = mongo.getDatabase(dapr["mongoDbName"]?.value.toString())
        val redis = Jedis(dapr["hostName"]?.value.toString(), dapr["portNumber"]?.value.toString().toInt())
        configureRouting(db, redis)
    }

}