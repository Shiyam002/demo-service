
package example.com.modules.todos

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import example.com.modules.dapr.MyHttpClient
import example.com.modules.todos.dto.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import redis.clients.jedis.Jedis
import kotlinx.serialization.encodeToString

class TodosService(private val db: MongoDatabase, private  val jedis: Jedis) : TodosRepository {
    override suspend fun create(todo: UserDataArray) = db.getCollection<UserData>("todos").insertMany(todo.data)
    override suspend fun fetchUserData(): UserDataArray {
        val users: List<User> = MyHttpClient.client.get("https://jsonplaceholder.typicode.com/users/").body()
        val userDataList = mutableListOf<UserData>()
        for (user in users) {
            val posts: List<Post> = MyHttpClient.client.get("https://jsonplaceholder.typicode.com/posts?userId=${user.id}").body()
            val userPostList = mutableListOf<PostComments>()
            for (post in posts) {
                val comments: List<Comment> =
                    MyHttpClient.client.get("https://jsonplaceholder.typicode.com/comments?postId=${post.id}").body()
                val userPost =  PostComments(post.id, post.userId, post.body, post.title, comments)
                userPostList.add(userPost)
            }
            val userData = UserData(user.id, user.name, user.username, user.email, userPostList)
            jedis.set("${user.id}", Json.encodeToString(userData))
            userDataList.add(userData)
        }

        return UserDataArray(userDataList)
    }
}
