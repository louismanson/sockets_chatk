import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket


private var socket: Socket? = null

fun main(args: Array<String>){
    try {
        var port = 25000
        val serverSocket = ServerSocket(port)
        println("Server Started and listening to the port 25000")

        //Server is running always. This is done using this while(true) loop
        while (true) {
            //Reading the message from the client
            socket = serverSocket.accept()
            var ar = socket?.getInputStream()
            var isr = InputStreamReader(ar)
            var br = BufferedReader(isr)
            var message = br.readLine()
            println("Client: " + message)
        }




    }catch (e: Exception){

    }finally {
        try {
            socket?.close()
        }catch (e: Exception){}
    }
}
