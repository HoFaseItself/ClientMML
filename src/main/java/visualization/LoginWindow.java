package visualization;

import visualization.ClientWindow;
import workWithFiles.ListOfServers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class LoginWindow extends JFrame implements ActionListener{

    /* Для того, чтобы впоследствии обращаться к содержимому текстовых полей, рекомендуется сделать их членами класса окна */
    JTextField loginField;
    JPasswordField passwordField;
//    JTextField hostField;
    JComboBox hostField;
    Box mainBox;
    ListOfServers listOfServers;

    public LoginWindow() throws FileNotFoundException {
        super("Вход в систему");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

// Настраиваем первую горизонтальную панель (для ввода логина)
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Логин:");
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        loginField = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(loginField);
// Настраиваем вторую горизонтальную панель (для ввода пароля)
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField(15);
        box2.add(passwordLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(passwordField);
//         Настраиваем вторую горизонтальную панель (для ввода хоста)
        Box box3 = Box.createHorizontalBox();
        JLabel hostLabel = new JLabel("Хост:      ");
//        hostField = new JTextField(15);
        listOfServers = new ListOfServers();
        hostField = new JComboBox(listOfServers.getServersList());
        box3.add(hostLabel);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(hostField);
// Настраиваем третью горизонтальную панель (с кнопками)
        Box box4 = Box.createHorizontalBox();
        JButton ok = new JButton("CONNECT");
        box4.add(Box.createHorizontalGlue());
        ok.addActionListener(this);
        box4.add(ok);

// Уточняем размеры компонентов
        loginLabel.setPreferredSize(passwordLabel.getPreferredSize());
// Размещаем три горизонтальные панели на одной вертикальной
        mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box3);
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box4);
        setContentPane(mainBox);
        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
//        sending authorization data to class visualization.ClientWindow
        ClientWindow.setLogin(loginField.getText());
        ClientWindow.setPassword(passwordField.getText());
        ClientWindow.setIpAddr(listOfServers.getServersList()[hostField.getSelectedIndex()]);
//        mainBox.setVisible(false);
//        close window for authorization
        dispose();
//        starting visualization.ClientWindow
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }
}