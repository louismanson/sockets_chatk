import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.Socket

private var socket: Socket? = null

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