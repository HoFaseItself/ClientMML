import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame implements Runnable{
//   задаем сетевые настройки для подключения
    private InetAddress ipAddress = InetAddress.getByName("78.46.211.26");
    private int port = 4000;
    static public Socket socket;
    static private DataInputStream inputStream;
    static private DataOutputStream outputStream;


    public Client(String name) throws UnknownHostException {
        super(name);
        setLayout(new FlowLayout());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        final JTextField t1 = new JTextField(10);
        final JButton b1 = new JButton("Send");

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1){
                    sendData(t1.getText());
                }
            }
        });
        add(t1);
        add(b1);
    }

    private static void sendData(Object obj) {
        try {
            outputStream.flush();
            outputStream.write(Integer.parseInt((String)obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
//   открываем сокет и потоки ввода вывода
            socket = new Socket(ipAddress, port);
            while (true) {
                outputStream = new DataOutputStream(socket.getOutputStream());
                inputStream = new DataInputStream(socket.getInputStream());
                System.out.println(inputStream.readLine());
//                JOptionPane.showMessageDialog(null, (String)inputStream.readObject());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }
}
