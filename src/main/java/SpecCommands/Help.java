package SpecCommands;

import visualization.ClientWindow;

public class Help extends SpecCommand {

    public Help()  {
        for (int i = 0; i < ClientWindow.messages.size(); i++) {
            String s = ClientWindow.messages.get(i).getInputMsg();
            outputWindow.printMsg(s);
        }
    }
}
