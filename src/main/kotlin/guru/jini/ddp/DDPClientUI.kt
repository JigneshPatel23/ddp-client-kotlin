package guru.jini.ddp

import java.awt.FlowLayout
import java.awt.HeadlessException
import javax.swing.*

/**
 * Created by Jignesh Patel on 19-06-2017.
 */
class DDPClientDialogBox @Throws(HeadlessException::class)
constructor(private val listener: EventListener) : JFrame("Send Message Box") {

    interface EventListener {
        fun onConnect(url: String)
        fun onMessage(message: String?)
    }

    private val urlLabel: JLabel
    private val messageLabel: JLabel
    private val urlField: JTextField
    private val messageArea: JTextArea
    private val sendButton: JButton
    private val connectButton: JButton

    init {
        layout = FlowLayout()

        urlLabel = JLabel("URL: ")
        add(urlLabel)

        urlField = JTextField("", 40)
        add(urlField)

        connectButton = JButton("Connect")
        connectButton.addActionListener { event ->
            if (event.source === connectButton) {
                val url = urlField.text
                listener.onConnect(url)
            }
        }
        add(connectButton)

        messageLabel = JLabel("Message: ")
        add(messageLabel)

        messageArea = JTextArea("", 10, 40)
        add(messageArea)

        sendButton = JButton("Send")
        sendButton.addActionListener { event ->
            if (event.source === sendButton) {
                val message = messageArea.text
                if (message != null) {
                    messageArea.text = ""
                    listener.onMessage(message)
                }
            }
        }
        add(sendButton)


    }
}
