package visualization;

import java.io.Serializable;

public class MsgFromServer implements Serializable{
    private String inputMsg;

    public String getInputMsg() {
        return inputMsg;
    }

    public MsgFromServer(String inputMsg) {
        this.inputMsg = inputMsg;
        ClientWindow.meSSage.add(inputMsg);
    }
}
