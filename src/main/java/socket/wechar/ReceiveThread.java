package socket.wechar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiveThread extends Thread{
    private Socket socket;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        ){
            while (true){
                String line = sin.readLine();
                out.println(line);

            }
        } catch (IOException e) {
//                e.printStackTrace();
        }
    }
}
