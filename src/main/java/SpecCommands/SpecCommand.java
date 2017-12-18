package SpecCommands;

import visualization.ClientWindow;
import visualization.OutputWindow;

public class SpecCommand extends Thread{

    public OutputWindow outputWindow;

//    {
//        outputWindow = new OutputWindow();
//    }

    public void clearingInputData(String inputCommand) throws InterruptedException {
     /*
    метод чистит массив данных прияных от сервера, посылает команду на сервер и ждет
    пока масиив полученных данных не начнет наполняться
     */
//      чистим массив данных
        ClientWindow.meSSage.clear();
//      посылаем сформированную команду на сервер
        ClientWindow.connection.sendString(inputCommand);
//      ждем пока новые данные не появятся в файле данных

//        System.out.println(ClientWindow.meSSage.size());

//        while (ClientWindow.meSSage.size() < 1);
        boolean check = true;
        while (check){
            sleep(1000);
            if (ClientWindow.meSSage.size() > 3) check = false;
        }
//        System.out.println("<============+=============>");
    }
}
