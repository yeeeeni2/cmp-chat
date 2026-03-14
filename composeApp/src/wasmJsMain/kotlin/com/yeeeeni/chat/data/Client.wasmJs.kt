package com.yeeeeni.chat.data

import com.yeeeeni.chat.API_BASE_URL
import com.yeeeeni.chat.SERVER_PORT
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.WebSockets

actual val client: HttpClient = HttpClient {
    install(WebSockets)

    defaultRequest {
        url(API_BASE_URL)
        port = SERVER_PORT
    }
}