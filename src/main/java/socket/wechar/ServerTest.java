package socket.wechar;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket =new ServerSocket(8088);
        System.out.println("等待连接");
        Socket accept = serverSocket.accept();
        System.out.println("连接成功");
        new SendThread(accept,"Client").start();
        new ReceiveThread(accept).start();

    }
}
