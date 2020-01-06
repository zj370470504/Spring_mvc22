package socket.wechar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendThread extends Thread{
    private Socket socket;
    private String name;

    public SendThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    //1、读
    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ){
            while (true){
                String line = in.readLine();
                System.out.println(name+">> "+line);

            }
        } catch (IOException e) {
//                e.printStackTrace();
        }
    }


}
