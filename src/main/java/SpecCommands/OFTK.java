package SpecCommands;

import visualization.ClientWindow;
import visualization.OutputWindow;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OFTK extends SpecCommand {

//    private String[] split;
    private String numberTg;
    private int countIteration;
    private final int TIK = 30000;

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
    public OFTK(String[] split) throws InterruptedException, ParseException {
//        this.split = split;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");

        numberTg = split[1];
        System.out.println(split[2]);
        countIteration = Integer.parseInt(split[2]);
        boolean checkWindow = true;
        String pat = "\\+{3}\\s+\\w+\\s+";
        for (int i = 0; i < countIteration; i++) {

            String command = String.format("DSP OFTK: LT=ONO, ONO=%s, DT=AT;", numberTg);
//        запрашиваем состояния OFC
            clearingInputData(command);
            boolean check = false;
            for (String line : ClientWindow.meSSage) {
                if (line.contains("+++")) {
                    String sssss = Pattern.compile(pat).matcher(line).replaceFirst("");
                    System.out.println(sssss + " <<<<<<<<<<<<<<<");
                    Date date1 = dateFormat.parse(sssss);
                    System.out.println(date1.toString() + " <=================");
                    date.add(date1);

                }
//            пропускаем первую часть
                if (line.contains("Sum of trunk")) check = true;
                if (check) {
//                System.out.println("TRUE<============");
//                if (line.contains("\\w+\\s+\\d+\\s+\\d")) {
//                    System.out.println("===========> CHECK");
                    String[] arr = line.split("\\s+");
                    if (arr.length > 1) {
                        System.out.println(arr[1]);
                        if (arr[1].equals("Free")) free.add(arr[2]);
                        if (arr[1].equals("Busy")) busy.add(arr[2]);
                        if (arr[1].equals("Block")) block.add(arr[2]);
                        if (arr[1].equals("Unknown")) lock.add(arr[4]);
                        if (arr[1].equals("Fault")) fault.add(arr[2]);
                        if (arr[1].equals("Installation")) installed = arr[3];
                    }
                }
            }
//        System.out.println(free.size());
//        System.out.println(busy.size());
//        System.out.println(block.size());
//        System.out.println(lock.size());
//        System.out.println(fault.size());
            String outputLine = String.format("Free: %s Busy: %s Block: %s Lock: %s Fault: %s Total: %s \r\n",
                    free.get(free.size() - 1), busy.get(busy.size() - 1), block.get(block.size() - 1),
                    lock.get(lock.size() - 1), fault.get(fault.size() - 1), installed);
//            запускаем окошко вывода
            if (checkWindow) {
                outputWindow = new OutputWindow();
                checkWindow = false;
            }
            outputWindow.printMsg(outputLine);
            Thread.sleep(TIK);
        }
    }
}
