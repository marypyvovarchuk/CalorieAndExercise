package bot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Options.java class provides
 * date options used in Food, Water and
 * Exercises classes.
 */

public class Options {

    /**
     * Method is used in case there is
     * need to check when was the last time
     * water balance updated
     *
     * @param last - Last date stored in String value
     * @return - Weight of chosen meal
     */
    public boolean controlDay(String last) {

        Date date = new Date();
        return formatDay(date).equals(last);
    }


    /**
     * Method is used in case there is
     * need to check when was the last time
     * water balance updated
     *
     * @param date - Date value to format
     * @return formattedDate   - date stored in String value
     */
    public String formatDay(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(date);
    }
}

