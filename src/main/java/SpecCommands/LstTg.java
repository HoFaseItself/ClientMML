package SpecCommands;


import visualization.ClientWindow;

import java.util.ArrayList;

public class LstTg extends SpecCommand{
    private ArrayList<String> tgNumber = new ArrayList<String>();
    private ArrayList<String> tgName = new ArrayList<String>();
    private ArrayList<String> opc = new ArrayList<String>();
    private ArrayList<String> dpc = new ArrayList<String>();
    private ArrayList<String> tkc = new ArrayList<String>();

    public LstTg() throws InterruptedException {
////      чистим массив данных
//        ClientWindow.meSSage.clear();
////      посылаем сформированную команду на сервер
//        ClientWindow.connection.sendString();
//
////      ждем пока новые данные не появятся в файле данных
//        while (ClientWindow.meSSage.size() < 1);
        clearingInputData("LST TG:;");

//        System.out.println(ClientWindow.meSSage.size());


        for (String line: ClientWindow.meSSage) {
//            System.out.println("----");
            if (line.contains("Master/Slave")) continue;

//            System.out.println("прошли на запись данных");
            if (line.contains("(Number of results =")) break;

            if (line.contains("Master")) {
                String[] arr = line.split("\\s+");
//                System.out.println(line);
//                System.out.println("0: " + arr[0] + " 1: " + arr[1] + " 2: " + arr[2] +" 3: " + arr[3] +" 4: " + arr[4] +" 5: " + arr[5] +" 6: " + arr[6] );
                tgNumber.add(arr[1]);
                tgName.add(arr[2]);
//                opc.add((Integer.parseInt(arr[3], 10) + ""));
                opc.add(arr[3]);
//                dpc.add((Integer.parseInt(arr[4], 10) + ""));
                dpc.add(arr[4]);
                System.out.println(tgNumber.size() + " " + tgName.size() + " " + opc.size() + " " + dpc.size());
            }
        }
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
//            System.out.println(String.format(param, opc.get(i), dpc.get(i), tgName.get(i), tgNumber.get(i), tkc.get(i)));
        }
    }

    private void clearingInputData(String inputCommand) throws InterruptedException {
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
//        sleep(1000);
        System.out.println("<============+=============>");
    }
}
