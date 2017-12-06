package SpecCommands;

import SpecCommands.FindCnacld;
import visualization.ClientWindow;
import visualization.OutputWindow;

import java.io.IOException;

public class CheckerInputCommand {

    public String checkingFirst(String inputCommand) throws IOException {
//        System.out.println(inputCommand);
        if (inputCommand.contains("#")) {
//            System.out.println(inputCommand);
            String[] xxx = inputCommand.split("\\s+");
            checkingSpecCom(inputCommand.split("\\s+"));
            return null;

        }
        else {
//            System.out.println("0");
            return inputCommand;
        }
    }

    private void checkingSpecCom(String[] split) throws IOException {
        if (split[0].toLowerCase().equals("#help")) {
            new Help();
//            OutputWindow outputWindow = new OutputWindow();
//
//            for (int i = 0; i < ClientWindow.getMessages().size(); i++) {
//                String s = ClientWindow.getMessages().get(i).getInputMsg();
//                outputWindow.printMsg(s);
//            }
        }
//
//        } else if (split[0].equals("#findPra")) {
//            new FindPra(split);
//        }
        else if (split[0].toLowerCase().equals("#findcnacld")){
            new FindCnacld(split);
        }
    }
}
