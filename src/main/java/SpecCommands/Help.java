package SpecCommands;

import visualization.ClientWindow;

public class Help extends SpecCommand {

    public Help()  {
        for (int i = 0; i < ClientWindow.getMessages().size(); i++) {
            String s = ClientWindow.getMessages().get(i).getInputMsg();
            outputWindow.printMsg(s);
        }
    }
}
