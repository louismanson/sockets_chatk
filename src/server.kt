import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*


private var socket: Socket? = null
private var serverSocket: ServerSocket? = null
private var inBuffer: DataInputStream? = null
private var outBuffer: DataOutputStream? = null
private var keyboard = Scanner(System.`in`)

private fun createConnection(port: Int){
    try{
        serverSocket = ServerSocket(port)
        println("Waiting for a client...")
        socket = serverSocket?.accept()
        println("Client connected")
        print("You: ")
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

private fun receiveData(){
    var st: String
    try {
        while (true){
            st = inBuffer!!.readUTF()
            println("\nClient: $st")
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
        send(keyboard.nextLine())
    }
}

private fun closeConnection() {
    try {
        inBuffer?.close()
        outBuffer?.close()
        socket?.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        println("Connection closed")
        System.exit(0)

    }
}

private fun executeConnection(puerto: Int) {
    val thread = Thread(Runnable {
        while (true) {
            try {
                createConnection(puerto)
                flow()
                receiveData()
            } finally {
                closeConnection()
            }
        }
    })
    thread.start()
}

@Throws(IOException::class)
fun main(args: Array<String>){
    val buffer = BufferedReader(InputStreamReader(System.`in`))
    print("Port [25000 default]: ")
    var port =  buffer.readLine()
    if(port.isEmpty()) port = "25000"
    executeConnection(port.toInt())
    writeData()

}


