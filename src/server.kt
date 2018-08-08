import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread


private var socket: Socket? = null
private var serverSocket: ServerSocket? = null
private var inBuffer: DataInputStream? = null
private var outBuffer: DataOutputStream? = null
internal var escaner = Scanner(System.`in`)


private fun createConnection(port: Int){
    try{
        serverSocket = ServerSocket(port)
        println("waiting...")
        socket = serverSocket?.accept()
        println("OK")
    }catch (e: Exception){
        e.printStackTrace()
        System.exit(0)
    }
}

private fun flow(){
    try {
        inBuffer = DataInputStream(socket?.getInputStream())
        outBuffer = DataOutputStream(socket?.getOutputStream())
        outBuffer?.flush()
    }catch (e: IOException){
        e.printStackTrace()
    }
}

private fun reciveData(){
    var st: String
    try {
        while (true){
            st = inBuffer!!.readUTF()
            println("Client: $st")
            print("You: ")
        }
    }catch (e: IOException){
        closeConnection()
    }
}

private fun send(message: String) {
    try {
        outBuffer?.writeUTF(message)
        outBuffer?.flush()
    } catch (e: IOException) {

    }
}

private fun writeData() {
    while (true) {
        print("You: ")
        send(escaner.nextLine())
    }
}

private fun closeConnection() {
    try {
        inBuffer?.close()
        outBuffer?.close()
        socket?.close()
    } catch (e: IOException) {
        println("Excepción en cerrarConexion(): " + e.message)
    } finally {
        println("Conversación finalizada....")
        System.exit(0)

    }
}

private fun executeConnection(puerto: Int) {
    val thread = Thread(Runnable {
        while (true) {
            try {
                createConnection(puerto)
                flow()
                reciveData()
            } finally {
                closeConnection()
            }
        }
    })
    thread.start()
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


