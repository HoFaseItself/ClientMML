import network.ServerForTest;
import visualization.LoginWindow;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        start programm
        new ServerForTest();
        new LoginWindow();
    }
}
