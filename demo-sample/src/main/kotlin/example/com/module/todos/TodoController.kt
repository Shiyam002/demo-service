package example.com.modules.todos

import example.com.modules.todos.dto.UserData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import redis.clients.jedis.Jedis
import kotlinx.serialization.json.Json

fun Route.todosController(todosService: TodosRepository, jedis:Jedis) {

    get("/users") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id != null) {
            val result =  jedis.get("$id")
            call.respond(HttpStatusCode.OK, Json.decodeFromString<UserData>(result))
        } else {
            val result = todosService.fetchUserData()
            todosService.create(result)
            call.respond(HttpStatusCode.OK, result.data)
        }
    }

}