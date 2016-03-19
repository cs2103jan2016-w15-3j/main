package parser;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExperimentParseDate {
    public static void main(String args[]) {
        PrettyTime ptime = new PrettyTime();

        System.out.println(ptime.format(new Date())); // today

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1); // yesterday

        System.out.println(ptime.format(cal.getTime()));

        cal.add(Calendar.YEAR, -10);// 10 years back

        System.out.println(ptime.format(cal.getTime()));

        List<Date> dates = new PrettyTimeParser().parse("I'm going to the beach tomorrow");
        System.out.println("Pretty time: " + dates.get(0));
    }

    public static void parseDate(String input) {
        List<Date> dates = new PrettyTimeParser().parse("I'm going to the right now!");
        System.out.println(dates);
    }
}
