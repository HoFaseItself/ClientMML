package SpecCommands;

import visualization.ClientWindow;
import visualization.MsgFromServer;

import java.io.*;
import java.util.ArrayList;


public class FindCnacld extends SpecCommand{
    private int count;

    public FindCnacld(String[] command) throws IOException, ClassNotFoundException, InterruptedException {

        count = Integer.parseInt(command[1]);

//      читстим массив данных от сервера
        ClientWindow.meSSage.clear();
        int startMessages  = ClientWindow.meSSage.size();
//      посылаем сформированную команду на сервер
        ClientWindow.connection.sendString(creatingCommandLine());

//      ждем пока новые данные не появятся в файле данных
        while (startMessages >= ClientWindow.meSSage.size()) {}

        for (int i = startMessages; i < ClientWindow.meSSage.size(); i++) {
            outputWindow.printMsg(ClientWindow.meSSage.get(i));
        }

    }

    private void findingPreffex(String line){
        if (line.contains("No matching result is found")) count--;

    }
    private String creatingCommandLine () {
        return String.format("LST CNACLD:LP=0, PFX=K'%d;", count);
    }
}
