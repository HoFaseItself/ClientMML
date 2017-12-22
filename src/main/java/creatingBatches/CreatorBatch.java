package creatingBatches;

import visualization.OutputWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreatorBatch {

//    public static OutputWindow outputWindow;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input type of creating commands:");
        String type = reader.readLine();

        String inputLine = "";
//        outputWindow = new OutputWindow();
        while (true) {
            System.out.println("Input prefixes, you can divided it only by the ',' :");
            inputLine =  reader.readLine();
            if (inputLine.contains("quit")) break;
            prepareBeforeCommand(inputLine.split(","), type);
        }
    }

    private static void prepareBeforeCommand(String[] split, String type) {
        if ("clrdsn".equals(type)) {
            new Clrdsn(split);
        }
    }
}
