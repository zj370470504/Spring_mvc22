package socket.simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务端开启，等待客户端连接。");
        Socket accept = serverSocket.accept();
        System.out.println("客户端已连接："+accept.getInetAddress().getHostAddress());
        try(
                //1、等待客服端发消息
                BufferedReader in = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                //2、向客户端发消息
                BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(accept.getOutputStream());

        ){
            //1、等待客服端发消息
            System.out.println("Client > " + in.readLine());
            //2、向客户端发消息
            //读命令行的一行
            String line = sin.readLine();
            out.println(line);
            out.flush();
        }


    }
}
