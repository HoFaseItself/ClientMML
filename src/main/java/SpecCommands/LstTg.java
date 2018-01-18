package SpecCommands;


import visualization.ClientWindow;
import visualization.OutputWindow;

import java.util.ArrayList;

public class LstTg extends SpecCommand{
    private ArrayList<String> tgNumber = new ArrayList<String>();
    private ArrayList<String> tgName = new ArrayList<String>();
    private ArrayList<String> opc = new ArrayList<String>();
    private ArrayList<String> dpc = new ArrayList<String>();
    private ArrayList<String> tkc = new ArrayList<String>();

    public LstTg() throws InterruptedException {

        clearingInputData("LST TG:;");

        for (String line: ClientWindow.meSSage) {

            if (line.contains("Master/Slave")) continue;
            if (line.contains("(Number of results =")) break;

            if (line.contains("Master")) {
                String[] arr = line.split("\\s+");
                tgNumber.add(arr[1]);
                tgName.add(arr[2]);
                opc.add(arr[3]);
                dpc.add(arr[4]);
                System.out.println(tgNumber.size() + " " + tgName.size() + " " + opc.size() + " " + dpc.size());
            }
        }
        outputWindow = new OutputWindow();
        outputWindow.printMsg("Total number of trunks: " + tgNumber.size() + "\t");
        System.out.println("finish load");
        for (int i = 0; i < tgNumber.size(); i++) {
            clearingInputData(String.format("LST TKC: TG=%s;", tgNumber.get(i)));
            for (String line : ClientWindow.meSSage){
                if (line.contains("Master")) continue;

                if (line.contains("ISUP")) {
                    String[] massif = line.split("\\s+");
                    for (int j = 0; j < massif.length; j++) {
                        System.out.print(j + ": " + massif[j] + " ");
                    }
                    System.out.println("TG: " + tgNumber.get(i));
                    tkc.add(line.split("\\s+")[2]);
                }
            }
        }
        String softxName = "Softx";
        clearingInputData("LST LDNSET: LP=0;");
        for (String line : ClientWindow.meSSage){
            if (line.contains("Local DN set name")) softxName += line.split("=")[1];
        }

        String param = "%s %s %s Scala %s filial MedId %s %s";
        for (int i = 0; i < tgNumber.size(); i++) {
            outputWindow.printMsg(String.format(param, softxName, opc.get(i), dpc.get(i), tgName.get(i), tgNumber.get(i), tkc.get(i)));
        }
    }

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
            System.out.println(ClientWindow.meSSage.size() + " " + ClientWindow.meSSage.get(ClientWindow.meSSage.size()-1));
            if (ClientWindow.meSSage.size() > 0) check = false;
        }
//        System.out.println("<============+=============>");
    }
}
