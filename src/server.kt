import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket


private var socket: Socket? = null
private var inBuffer: DataInputStream? = null
private var outBuffer: DataOutputStream? = null

fun createConextion(ip: String, port: Int) {
    try {
        socket = Socket(ip,port)
        println("Server Started and listening to the port 25000")
    }catch (e: Exception){
        e.printStackTrace()
    }
}

fun dataFlow(){
    try {
        inBuffer = DataInputStream(socket?.getInputStream())
        outBuffer = DataOutputStream(socket?.getOutputStream())
        outBuffer?.flush()
    }catch (e: Exception){
        e.printStackTrace()
    }
}

fun sendData(message: String){
    try {
        outBuffer?.writeUTF(message)
        outBuffer?.flush()
    }catch (e: Exception){

    }
}

fun closeConextion(){
    try {
        outBuffer?.close()
        inBuffer?.close()
        socket?.close()
    }catch (e: Exception){
        e.printStackTrace()
    }finally {
        System.exit(0)
    }

}

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
