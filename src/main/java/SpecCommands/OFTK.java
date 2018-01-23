package SpecCommands;

import visualization.ClientWindow;
import visualization.OutputWindow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class OFTK extends SpecCommand {

//    private String[] split;
    private String numberTg;
    private int countIteration;
    private int TIK = 54;

//    данные для создания выходной таблицы
    private ArrayList<String> free = new ArrayList<String>();
    private ArrayList<String> busy = new ArrayList<String>();
    private ArrayList<String> block = new ArrayList<String>();
    private ArrayList<String> lock = new ArrayList<String>();
    private ArrayList<String> fault = new ArrayList<String>();
    private ArrayList<Date> date =  new ArrayList<Date>();
    private String installed;

/*
    * первый элемент массива индификатор команды
    * второй элемент номер транковой группы
    * третий количество итераций в часах(в первом приближении будем делать в минутах)
 */
    public OFTK(String[] split) throws InterruptedException, ParseException, IOException {
//        this.split = split;


        BufferedWriter bw = new BufferedWriter(new FileWriter("c:/java/oftkOut.txt"));
        DateFormat inpFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
        DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        numberTg = split[1];
        countIteration = Integer.parseInt(split[2]);
        TIK = TIK * 1000;
//        TIK = Integer.parseInt(split[3]) * 1000;
        boolean checkWindow = true;
        String pat = "\\+{3}\\s+\\w+\\s+";
        for (int i = 0; i < countIteration; i++) {
            Thread.sleep(TIK); // задержка
            String command = String.format("DSP OFTK: LT=ONO, ONO=%s, DT=AT;", numberTg);
//        запрашиваем состояния OFC
            clearingInputData(command);
            boolean check = false;

            for (String line : ClientWindow.meSSage) {
                //  добавляем дату выполнения команды
                if (line.contains("+++")) {
                    String sssss = Pattern.compile(pat).matcher(line).replaceFirst("");
                    Date date1 = inpFormat.parse(sssss);
//                    System.out.println("=============> " + date1.toString() + " <===============");
                    date.add(date1);
                }
//            пропускаем первую часть
                if (line.contains("Sum of trunk")) check = true;
                if (check) {
                    String[] arr = line.split("\\s+");
                    if (arr.length > 1) {
//                        System.out.println(arr[1]);
//                        заполняем поля
                        if (arr[1].equals("Free")) free.add(arr[2]);
                        if (arr[1].equals("Busy")) busy.add(arr[2]);
                        if (arr[1].equals("Block")) block.add(arr[2]);
                        if (arr[1].equals("Unknown")) lock.add(arr[4]);
                        if (arr[1].equals("Fault")) fault.add(arr[2]);
                        if (arr[1].equals("Installation")) installed = arr[3];
                    }
                }
            }

            String outputLine = String.format("Free: %s Busy: %s Block: %s Lock: %s Fault: %s Total: %s Time: %s\r",
                    free.get(free.size() - 1), busy.get(busy.size() - 1), block.get(block.size() - 1),
                    lock.get(lock.size() - 1), fault.get(fault.size() - 1), installed, outFormat.format(date.get(date.size() - 1)));
            String toFile = String.format("%s %s %s %s %s %s %s\r\n",
                    free.get(free.size() - 1), busy.get(busy.size() - 1), block.get(block.size() - 1),
                    lock.get(lock.size() - 1), fault.get(fault.size() - 1), installed, outFormat.format(date.get(date.size() - 1)));

            System.out.println(outFormat.format(date.get(date.size() - 1)) + " <<<<<<<<<<<<");
//            запускаем окошко вывода
//            if (checkWindow) {
//                outputWindow = new OutputWindow();
//                checkWindow = false;
//            }
//            outputWindow.printMsg(outputLine);
            bw.write(toFile);
//            Date startTime = new Date();
//            timeTik(startTime);
        }
        bw.flush();
        bw.close();
    }

    private void timeTik(Date startTime) {
        long tik = 0;
        while (tik >= 1000) {
            Date curentTime = new Date();
            tik = curentTime.getTime() - startTime.getTime();
        }
    }
}
