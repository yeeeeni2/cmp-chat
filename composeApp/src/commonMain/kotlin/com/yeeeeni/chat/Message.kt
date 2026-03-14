package com.yeeeeni.chat

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val username: String,
    val content: String
)