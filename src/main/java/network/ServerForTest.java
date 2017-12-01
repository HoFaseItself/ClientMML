package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class ServerForTest {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6000);
        Socket socket = serverSocket.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        while (true) {
            String s = br.readLine();
            if (s != null || s != "") {
                System.out.println("++++++++++++++++++");
                String input = br.readLine();
                bw.write("===> :" + input);
            }
        }
    }
}
