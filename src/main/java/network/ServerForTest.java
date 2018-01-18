package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class ServerForTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("Server Started:");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        while (true) {
            String s = br.readLine();
            if (s != null || s != "") {
//                System.out.println(input);
                bw.write("Answer from Server: ====> " + s + "1\n");
                System.out.println(s);

                if (s.contains("LST TG")) {
                    for (int i = 10; i >= 0; i--) {
                        System.out.println(i);
                        bw.write(String.format("Line number %d!", i));
//                        Thread.sleep(200);
                    }
                    bw.write("And Fly!!!");
                }
                bw.flush();
            }
        }
    }
}
