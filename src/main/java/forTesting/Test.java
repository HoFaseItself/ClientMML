package forTesting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String line = "+++    Cheboksary        2018-01-17 10:59:11+03:00";
        String pat = "\\+{3}\\s+\\w+\\s+";
        System.out.println(Pattern.compile(pat).matcher(line).replaceFirst(""));


//        Pattern pattern = Pattern.compile(regexp);

//        Matcher matcher = pattern.matcher(goodIp);
// matches() - true, find() - true
//        matcher = pattern.matcher(badIp);
    }
}
