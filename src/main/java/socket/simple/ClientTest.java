package socket.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        //客户端与服务端建立连接
        Socket socket = new Socket("localhost",8080);
        System.out.println("连接成功");
        try(
                //1、客户端给服务器端发消息
                BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ){
            //1、客户端给服务器端发消息
            String line = sin.readLine();
            out.println(line);
            out.flush();
            //2、收到服务器端的消息
            System.out.println("Service >"+in.readLine());

        }
    }
}
