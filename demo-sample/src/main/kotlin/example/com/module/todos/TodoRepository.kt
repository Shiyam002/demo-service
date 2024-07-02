package example.com.modules.todos

import com.mongodb.client.result.InsertManyResult
import example.com.modules.todos.dto.UserDataArray

interface TodosRepository {

    suspend fun create(todo: UserDataArray): InsertManyResult
    suspend fun fetchUserData(): UserDataArray

}