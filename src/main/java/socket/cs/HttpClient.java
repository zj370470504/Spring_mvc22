package socket.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpClient extends Thread{
    private Socket socket;

    public HttpClient(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        ){
            while (true) {
                String res = sin.readLine();
                if (res.equals("bye")){
                    break;
                }
                out.println(res);
                String content = in.readLine();
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
