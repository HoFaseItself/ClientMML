package SpecCommands;

import visualization.ClientWindow;
import visualization.OutputWindow;

public class Rtana extends SpecCommand{
    public Rtana() throws InterruptedException {
        String name = null, RSC = null, RSSC = null, category = null, route = null, rtName = null;

        String title = "Name \t*\tRSC \t*\tRSSC \t*\tCategory \t*\tRoute \t*\tRtName";
        String outPutLine = "%s \t*\t%s \t*\t%s \t*\t%s \t*\t%s \t*\t%s";
        clearingInputData("LST RTANA: MOD=MIX;");
        outputWindow = new OutputWindow();
        outputWindow.printMsg(title);
        for (String line : ClientWindow.meSSage){
            if (line.contains("=")) {
                String[] subLines = line.split("=");
                if (subLines[0].contains("Route Analysis Name")) name = subLines[1];
                else if (subLines[0].contains("Route Select Code")) RSC = subLines[1];
                else if (subLines[0].contains("Route Select Source Code")) RSSC = subLines[1];
                else if (subLines[0].contains("Customized caller type")) category = subLines[1];
                else if (subLines[0].contains("Route number")) route = subLines[1];
                else if (subLines[0].contains("Route Name")) rtName = subLines[1];
                else if (subLines[0].contains("Master/Slave type")) {
//                    if (name.length() > 12 && name.length() < 21) outPutLine = "%s \t\t%s \t%s \t%s \t%s \t%s";
//                    if (name.length() > 20) outPutLine = "%s \t%s \t%s \t%s \t%s \t%s";
                    outputWindow.printMsg(String.format(outPutLine, name, RSC, RSSC, category, route, rtName));
                }
            }
        }
    }
}
