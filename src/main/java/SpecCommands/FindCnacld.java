package SpecCommands;

import visualization.ClientWindow;

public class FindCnacld extends SpecCommand{
    public FindCnacld(String[] command) {

        int number = Integer.parseInt(command[1]);
        String senderCommand = String.format("LST CNACLD:LP=0, PFX=K'%d;", number);

//        if (ClientWindow.getMessages().size() != 0) ClientWindow.setMessages(null);

        ClientWindow.connection.sendString(senderCommand);
        for (int i = 0; i < ClientWindow.getMessages().size(); i++) {
            outputWindow.printMsg(ClientWindow.getMessages().get(i).getInputMsg());
        }

    }
}
