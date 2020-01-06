package socket.bs;

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
    public static Map<String, String> contentMap = new HashMap<>();

    static {
        contentMap.put("/hello","hello");
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        ){
            String request = null;
            String line = null;
            while ((line = in.readLine())!=null &&!line.equals("")){
                System.out.println(line);
                if (request == null) {
                    request = line;
                }
            }

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type:text/html;charset=utf-8 ");
            out.println();
            out.println("<html><head><title>HttpTest</title></head><body>");
            if (request != null) {
                String url = request.split(" ")[1];
                String content = contentMap.get(url);
                out.println(content != null ? content : "404");
            }
            out.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("客服端断开");
        }

    }
}
