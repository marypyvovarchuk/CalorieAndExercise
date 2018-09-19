package bot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Options.java class provides
 * properties used in Food, Water and
 * Exercises classes.
 */

public class Options {

    public boolean controlDay(String last) {
        Date date = new Date();
        return formatDay(date).equals(last);
    }


    public String formatDay(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}

