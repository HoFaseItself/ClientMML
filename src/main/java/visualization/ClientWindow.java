package visualization;

import network.TCPConnection;
import network.TCPConnectionListener;
import SpecCommands.CheckerInputCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener{

    public static ArrayList<MsgFromServer> getMessages() {
        return messages;
    }

    public static void setMessages(ArrayList<MsgFromServer> messages) {
        ClientWindow.messages = messages;
    }

    private static ArrayList<MsgFromServer> messages = new ArrayList<MsgFromServer>();


    public static ArrayList<String> outputFlow = new ArrayList<String>();
//    задаются переменные для того чтобы выбрать адресс сервера и авторизоваться на нем
    private static String IP_ADDR, login, password;


//    блок сеттеров для переменных авторизации
    public static void setIpAddr(String ipAddr) {
        IP_ADDR = ipAddr;
    }

    public static void setLogin(String login) {
        ClientWindow.login = login;
    }

    public static void setPassword(String password) {
        ClientWindow.password = password;
    }

    public static String getIpAddr() {
        return IP_ADDR;
    }

//    задаются переменные для диалогового окна
    private static final int PORT = 6000;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final JTextArea log = new JTextArea();
    public final JTextArea specPanel = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(log);   // поле в котором будет отображаться информация с сервера
    public final JScrollPane scrollSpecPane = new JScrollPane(specPanel);
    private final JTextField fieldInput = new JTextField();

//    Socket соединение через клас TCPConnection
    public static TCPConnection connection;

    public ClientWindow() {
//        настраиваем диалоговое окно
//        при закрытии окна закрываем программу
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        размеры окна
        setSize(WIDTH, HEIGHT);
//        при открытии окна, расположение его по центру
        setLocationRelativeTo(null);
//        setAlwaysOnTop(true);
//        устанавливаем, что поле лога будет не редактируемым
        log.setEditable(false);
//        установливаем область ввода
        log.setLineWrap(true);
//        устанавливаем тип редактирования и область ввода в поле спецпанели
        specPanel.setEditable(false);
        specPanel.setLineWrap(true);
//        добавлем пролистываемые панели на поле
        add(scrollPane, BorderLayout.CENTER);
        add(scrollSpecPane, BorderLayout.NORTH);
//        добавляем прослушивание активностей в водимом поле (для того чтобы после энтера передавалось сообщение)
        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);

        setVisible(true);

        try {
//        сейчас устанавливаю дефолтные пароли для тестов, потом надо удалить
            login = "smednyh";
            password = "SoftX3000";
//        инициализируем присоединение к серверу
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) { printMsg("Connection exception: " + e);}
    }

//        формируем стороку команды для авторизации
    private String logining() {
        return "LGI:op=\"" + login + "\", PWD =\"" + password + "\", SER=\"" + IP_ADDR + "---O&M System\";" + "\r\n";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if (msg.equals("")) return;
        fieldInput.setText(null);
//        посылаем команду на сервер
        try {
            connection.sendString(new CheckerInputCommand().checkingFirst(msg));
            System.out.println(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        connection.sendString(msg);
    }


//        after connected send command for authorization
    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
        String loginSrtring = logining();
        connection.sendString(loginSrtring);
    }


//        sending message (String)
    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
//        здесь у нас значения, которые приходят от сервера

        messages.add(new MsgFromServer(value));
        outputFlow.add(value);
    }


//    reaction to Disconnect
    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close!");
    }


//    reaction to connection
    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

//    printing message
    private synchronized void printMsg(final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               log.append(msg + "\n");
               log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    public synchronized void printMsgSpec(final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                specPanel.append(msg + "\n");
                specPanel.setCaretPosition(specPanel.getDocument().getLength());
            }
        });
    }
    public void connectToServer() {
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }
}



//LGI:op="smednyh", PWD ="SoftX3000", SER"10.188.4.154---O&M System";