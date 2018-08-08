import java.io.*
import java.net.InetAddress
import java.net.Socket
import java.util.*

private var socket: Socket? = null
private var inBuffer: DataInputStream? = null
private var outBuffer: DataOutputStream? = null
internal var teclado = Scanner(System.`in`)


private fun createConnection(ip: String, port: Int) {
    try {
        socket = Socket(ip,port)
        println("Server Started and listening to the port 25000")
    }catch (e: Exception){
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

private fun reciveData(){
    var st = ""
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

private fun writeData90{
    var input: String
    while (true){
        println("You: ")
        input = teclado.nextLine()
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
        println("end")
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

        }finally {
            closeConnection()
        }
    })
    thread.start()

}

fun main (arg: Array<String>){
    while (true){
        val buffer = BufferedReader(InputStreamReader(System.`in`))
        print("you: ")
        var message =  buffer.readLine()

        try {
            var host = "localhost"
            var port = 25000
            var address = InetAddress.getByName(host)
            socket = Socket(address,port)


            var os = socket?.getOutputStream()
            var osw = OutputStreamWriter(os)
            var bw = BufferedWriter(osw)
            var sendMessage = message+"\n"
            bw.write(sendMessage)
            bw.flush()


        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            try {
                socket?.close()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }



}