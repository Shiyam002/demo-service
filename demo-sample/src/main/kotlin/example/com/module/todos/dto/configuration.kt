package example.com.modules.todos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConfigValue(
    val value: String
)