import java.io.*
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket
import java.util.*

private var socket: Socket? = null
private var inBuffer: DataInputStream? = null
private var outBuffer: DataOutputStream? = null
private var keyboard = Scanner(System.`in`)


private fun createConnection(ip: String, port: Int) {
    try {
        println("Server Started and listening to the port 25000")
        socket = Socket(InetAddress.getByName(ip),port)
        print("You: ")
    }catch (e: ConnectException){
        e.printStackTrace()
    }
}

private fun dataFlow(){
    try {
        inBuffer = DataInputStream(socket?.getInputStream())
        outBuffer = DataOutputStream(socket?.getOutputStream())
        outBuffer?.flush()
    }catch (e: IOException){
        e.printStackTrace()
    }
}

private fun sendData(message: String){
    try {
        outBuffer?.writeUTF(message)
        outBuffer?.flush()
    }catch (e: IOException){

    }
}

private fun receiveData(){
    var st: String
    try {
        while (true){
            st = inBuffer!!.readUTF()
            println("\nServer: $st")
            print("You: ")
        }
    }catch (e: IOException){
        closeConnection()
    }
}

private fun writeData(){
    var input: String
    while (true){
        print("You: ")
        input = keyboard.nextLine()
        if (input.isNotEmpty()){
            sendData(input)
        }
    }
}

private fun closeConnection(){
    try {
        outBuffer?.close()
        inBuffer?.close()
        socket?.close()
        println("Connection closed")
    }catch (e: IOException){
        e.printStackTrace()
    }finally {
        System.exit(0)
    }
}

fun executeConnection(ip: String, port: Int){
    val thread = Thread(Runnable {
        try {
            createConnection(ip,port)
            dataFlow()
            receiveData()
        }finally {
            closeConnection()
        }
    })
    thread.start()
}

@Throws(IOException::class)
fun main (arg: Array<String>){

    val buffer = BufferedReader(InputStreamReader(System.`in`))

    print("IP [localhost default]: ")
    var ip = buffer.readLine()
    if(ip.isEmpty()){
        ip = "localhost"
    }

    print("Port [25000 default]: ")
    var port = buffer.readLine()
    if (port.isEmpty()) {
        port = "25000"
    }

    executeConnection(ip, port.toInt())
    writeData()

}