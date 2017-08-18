package guru.jini.ddp


import javax.swing.JFrame

/**
 * Created by Jignesh Patel on 16-06-2017.
 */
object DDPClient {

    private var ddpSocket: DDPSocket? = null
    //    private static Scanner scanner;

    @JvmStatic
    fun main(args: Array<String>) {
        //        scanner = new Scanner(System.in);

        // Get Socket URL
        println("DDP Client => Please enter your websocket URL : ")

        val dialogBox = DDPClientDialogBox(object : DDPClientDialogBox.EventListener {
            override fun onConnect(url: String) {
                // Create / Connect DDP Client
                ddpSocket = DDPSocket(url)
                ddpSocket!!.open()
            }

            override fun onMessage(message: String?) {
                if (ddpSocket != null) {
                    ddpSocket!!.sendMessage(message)
                }
            }
        })
        dialogBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        dialogBox.setSize(600, 500)
        dialogBox.setVisible(true)


        //        String url = scanner.next();


        // Message Communication
        //        String input = null;
        //        while(!(input = scanner.next()).equalsIgnoreCase("exit")) {
        //            ddpSocket.sendMessage(input);
        //        }

        // Exit System
        //        {
        //            System.out.println("Exit! System Shutdown!");
        //            System.exit(1);
        //        }
    }
}
