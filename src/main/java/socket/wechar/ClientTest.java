package socket.wechar;


import java.io.IOException;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        Socket socket =new Socket("localhost",8088);
        new SendThread(socket,"Server").start();
        new ReceiveThread(socket).start();
    }
}
