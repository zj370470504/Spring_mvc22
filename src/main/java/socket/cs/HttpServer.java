package socket.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer extends Thread{
    private Socket socket;

    public HttpServer(Socket socket) {
        this.socket = socket;
    }
    private static Map<String, String> contentMap = new HashMap<>();

    static {
        contentMap.put("index", "Welcome!");
        contentMap.put("hello", "How are you?");
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        ){
            while (true){
                String line = in.readLine();
                if (line.equals("bye")  || line == null){
                    break;
                }
                String s = contentMap.get(line.trim());
                out.println(contentMap != null? s : "404");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("客服端断开");
        }

    }
}
