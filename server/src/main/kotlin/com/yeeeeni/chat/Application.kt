package com.yeeeeni.chat

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = HOST, module = Application::module).start(
        wait = true
    )
}

fun Application.module() {
    install(WebSockets)
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }

        val clients = mutableSetOf<DefaultWebSocketSession>()

        webSocket(path = "/chat") {
//          웹소켓에 연결된 모든 클라이언트에 메세지 전송
            clients.add(this)
//          incoming (서버 -> client)
//          outgoing (client -> 서버)
//          실시간 통신
            incoming
                //Channel의 데이터를 Flow 형태로 수신 가능
                .receiveAsFlow()
                .onCompletion {
                    clients.remove(this@webSocket)
                }
                .collect { frame ->
                    when (frame) {
                        is Frame.Text -> {
                            val receivedText = frame.readText()

                            clients.forEach { session ->
                                session.send(Frame.Text(receivedText))
                            }
                        }
                        else -> {
                            println("지원하지 않는 문자열입니다.")
                        }
                    }
                }
        }
    }
}