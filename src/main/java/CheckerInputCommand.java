import java.io.IOException;

public class CheckerInputCommand {

    public String checkingFirst(String inputCommand) throws IOException {
//        System.out.println(inputCommand);
        if (inputCommand.contains("#")) {
//            System.out.println(inputCommand);
            String[] xxx = inputCommand.split("\\s+");
//            for (int i = 0; i <xxx.length; i++) {
//                System.out.println(xxx[i] + i);
//            }
//            System.out.println(xxx[0]);
            checkingSpecCom(inputCommand.split("\\s+"));
            return null;
        }
        else {
//            System.out.println("0");
            return inputCommand;
        }
    }

    private String checkingSpecCom(String[] split) throws IOException {
//        if (split[0].equals("#help")) {
//            new HelpOwlEye();
//
//        } else if (split[0].equals("#findPra")) {
//            new FindPra(split);
//        }
//        else if (split[0].equals("#findCnacld")){
//            new FindCnacld(split);
//        }
        return null;
    }
}
