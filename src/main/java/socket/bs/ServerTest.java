package socket.bs;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("开启服务器，等待客户端连接。。。");
        while (true){
            Socket accept = serverSocket.accept();
            Thread thread = new HttpServer(accept);
            System.out.println("客服端已连接");
            thread.start();
        }
    }

}
