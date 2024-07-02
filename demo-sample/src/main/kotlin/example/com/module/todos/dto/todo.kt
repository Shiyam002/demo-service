package example.com.modules.todos.dto

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String,val username: String, val email: String)

@Serializable
data class Post(val id: Int,val userId: Int, val title: String, val body: String)

@Serializable
data class PostComments(val id: Int,val userId: Int, val title: String, val body: String, val comments: List<Comment>)

@Serializable
data class Comment(val id: Int,val postId: Int, val name: String, val email: String, val body: String)

@Serializable
data class UserData(val id: Int, val name: String,val username: String, val email: String, val posts: List<PostComments>)

@Serializable
data class UserDataArray(val data: List<UserData>)