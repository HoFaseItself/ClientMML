package visualization;

import javax.swing.*;
import java.awt.*;

public class OutputWindow extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private final JTextArea panel = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(panel);

    public OutputWindow() {
        setSize(WIDTH, HEIGHT);
//        при открытии окна, расположение его по центру
        setLocationRelativeTo(null);
//        устанавливаем, что поле лога будет не редактируемым
        panel.setEditable(false);
//        установливаем область ввода
        panel.setLineWrap(true);

//        добавлем пролистываемые панели на поле
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }


    //    printing message
    public synchronized void printMsg(final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                panel.append(msg + "\n");
                panel.setCaretPosition(panel.getDocument().getLength());
            }
        });
    }
}
