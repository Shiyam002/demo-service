package example.com.plugins

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import example.com.modules.todos.TodosService
import example.com.modules.todos.todosController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import redis.clients.jedis.Jedis

fun Application.configureRouting(db: MongoDatabase, jedis: Jedis) {
    val todoService = TodosService(db, jedis)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("todos") {
            todosController(todoService, jedis)
        }
    }
}