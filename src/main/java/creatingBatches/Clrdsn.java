package creatingBatches;

public class Clrdsn {
    private String DSP = "900";
    private String CSRC = "904";
    private String RDCX = "16";


    public Clrdsn(String[] split) {
        String output = null;
        for (String line: split){
            if (line.contains("-")){
                String[] path = line.split("-");
//                System.out.println(path[0] + " ===== " + path[1]);


                long start = Long.parseLong(path[0].trim());
                long end = Long.parseLong(path[1].trim());
                for (int i = 0; i <= end - start; i++) {
                    output = creatingCommand((start + i) + "");
//                    CreatorBatch.outputWindow.printMsg(output);
                    System.out.println(output);
                }

            }
            else {
                output = creatingCommand(line.trim());
//                CreatorBatch.outputWindow.printMsg(output);
                System.out.println(output);
            }

        }
    }

    private String creatingCommand(String line) {
        String result;
        result = String.format("ADD CLRDSN: DSP=%s, CLI=K'%s, DSTYPE=NORT, DAI=ALL, FUNC=ATT, CSRC=%s, CHG=0, CMN=NO," +
                "RDCX=%s;", DSP, line, CSRC, RDCX);
        return result;
    }
}
