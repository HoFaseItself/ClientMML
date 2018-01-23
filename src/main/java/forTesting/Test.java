package forTesting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws ParseException {


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");




        String line = "+++    Cheboksary        2018-01-17 10:59:11+03:00";
        String pat = "\\+{3}\\s+\\w+\\s+";
        String outDate =  Pattern.compile(pat).matcher(line).replaceFirst("");
        System.out.println(outDate);

        Date date1 = dateFormat.parse(outDate);

        System.out.println(dateFormat1.format(date1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = sdf.parse(outDate);
        System.out.println(">>>" + sdf.format(date2));



//        Pattern pattern = Pattern.compile(regexp);

//        Matcher matcher = pattern.matcher(goodIp);
// matches() - true, find() - true
//        matcher = pattern.matcher(badIp);
    }
}
