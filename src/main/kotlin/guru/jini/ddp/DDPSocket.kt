package guru.jini.ddp

import com.google.gson.JsonParser
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Created by Jignesh Patel on 16-06-2017.
 */
class DDPSocket(private val url: String) : WebSocketListener() {
    private var webSocket: WebSocket? = null

    fun open() {
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build()

        val request = Request.Builder()
                .url(url)
                .build()

        okHttpClient.newWebSocket(request, this)
    }

    fun sendMessage(message: String?) {
        println("DDPSocket sendMessage => " + message)
        webSocket!!.send(message)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        this.webSocket = webSocket
        println("DDPSocket onOpen => " + response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        println("DDPSocket onMessage => " + text)
        pingAutoReply(text)
    }

    private fun pingAutoReply(text: String) {
        val jsonElement = JsonParser().parse(text)
        if (jsonElement.isJsonObject()) {
            val jsonObject1 = jsonElement.getAsJsonObject()
            if (jsonObject1.has(MSG)) {
                val msg = jsonObject1.get(MSG).getAsString()
                if (msg.equals(PING, ignoreCase = true)) {
                    sendMessage(MSG_PONG)
                }
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        println("DDPSocket onMessage => " + bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        println("DDPSocket onClosing =  code = [$code], reason = [$reason]")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        println("DDPSocket onClosed =  code = [$code], reason = [$reason]")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response) {
        super.onFailure(webSocket, t, response)
        println("DDPSocket onFailure =  t = [$t], response = [$response]")
    }

    companion object {

        val MSG = "msg"
        val PING = "ping"
        val MSG_PONG = "{\"msg\":\"pong\"}"
    }
}
