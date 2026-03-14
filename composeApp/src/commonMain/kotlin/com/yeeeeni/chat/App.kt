package com.yeeeeni.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yeeeeni.chat.component.ChatListArea
import com.yeeeeni.chat.component.Header
import com.yeeeeni.chat.data.client
import com.yeeeeni.chat.theme.AppColors
import com.yeeeeni.chat.component.FieldArea
import com.yeeeeni.chat.Message
import com.yeeeeni.chat.theme.AppTypography
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(typography = AppTypography()) {
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val textFieldState = rememberTextFieldState()

        val username = remember {
            getPlatform().name
        }

        val messages = remember {
            mutableStateListOf<Message>()
        }

        val session = produceState<WebSocketSession?>(null) {
            client.webSocket(path = "/chat") {
                value = this

                incoming
                    .receiveAsFlow()
                    .collect { frame ->
                        when (frame) {
                            is Frame.Text -> {
                                val receivedText = frame.readText()
                                messages.add(Json.decodeFromString(receivedText))
                            }

                            else -> {
                                println("지원하는 형식이 아닙니다.")
                            }
                        }
                    }
            }
        }

        Column(modifier = Modifier.fillMaxSize().background(AppColors.White).safeDrawingPadding()) {
            Header(title = "Welcome")

            Column(modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 16.dp)) {
                ChatListArea(
                    modifier = Modifier.weight(1f),
                    state = lazyListState,
                    ownerUsername = username,
                    messages = messages
                )

                FieldArea(
                    modifier = Modifier.padding(bottom = 24.dp),
                    textFieldState = textFieldState,
                    enabled = session.value != null && textFieldState.text.isNotEmpty(),
                    onSend = { messageText ->
                        coroutineScope.launch {
                            val message = Message(username = username, content = messageText)
                            session.value?.send(Frame.Text(Json.encodeToString(message)))

                            textFieldState.clearText()
                            lazyListState.scrollToBottom()
                        }
                    }
                )
            }
        }
    }
}

private suspend fun LazyListState.scrollToBottom() = scrollToItem(Int.MAX_VALUE)