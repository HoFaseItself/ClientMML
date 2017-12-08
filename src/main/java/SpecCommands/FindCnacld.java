package SpecCommands;

import visualization.ClientWindow;

import java.io.*;
import java.util.HashMap;


public class FindCnacld extends SpecCommand{
    private int number;
    private HashMap<String, String> inputMapData = new HashMap<String, String>();

    public FindCnacld(String[] command) throws IOException, ClassNotFoundException, InterruptedException {

        number = Integer.parseInt(command[1]);

//      чистим массив данных
        ClientWindow.meSSage.clear();
//      посылаем сформированную команду на сервер
        ClientWindow.connection.sendString(creatingCommandLine());

//      ждем пока новые данные не появятся в файле данных
        while (ClientWindow.meSSage.size() < 1);


//        ГДЕ ТО ЗДЕСЬ КОСЯК С NUMBER НУЖНО ПРАВИЛЬНО ПОСТАВИТЬ ВЫЛЕТ НА НОЛЬ

//        потенциальные проблемы с тем, что будет чекать все строки, надо придумть как останавливаться на найденном
        for (String line: ClientWindow.meSSage) {
            if (number == 0) outputWindow.printMsg("This number is missing!");
            if (line.contains("No matching result is found")) {
                number /=10;
                continue;
            }
            else if (line.contains("RETCODE = 85001")) outputWindow.printMsg("WARNING: The querying result is larger than the MAX parameter!");
            else if (line.contains("Number of results =")) continue;
            else if (line.contains("=")) {
                String[] lineSplit = line.split("=");
                System.out.println(lineSplit[0].trim() + " <=hashMap=> " + lineSplit[1].trim());
                inputMapData.put(lineSplit[0].trim(), lineSplit[1].trim());
            }

//            findingPreffex(ClientWindow.meSSage.get(i));
//            outputWindow.printMsg(ClientWindow.meSSage.get(i));
        }

    }

    private void findingPreffex(String line){
        if (line.contains("No matching result is found")) number--;
        else if (line.contains("Call Prefix service attribute information")) printData();
    }

    private void printData() {
        String LDNSET, PREFIX, MIN, MAX, RSC, ATTRIBUTE, DESC, SPECIAL;

        for (String line: ClientWindow.meSSage){
            if (line.contains("Local DN set")) LDNSET = String.format(line,"\\d");
            else if (line.contains("Call prefix")) PREFIX = String.format(line,"(\\d)|(\\w\\d)");

        }
    }

    private String creatingCommandLine () {
        return String.format("LST CNACLD:LP=0, PFX=K'%d;", number);
    }
}
