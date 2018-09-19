package bot;

import java.util.Date;

/**
 * Purpose of Water.java: provides
 * options used to count water balance
 * each new day.
 *
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class Water extends Options {

    public int waterBalance = 0;
    public Date lastDate = new Date();



    public void getReply(int waterCurrBalance) {

        String lastWaterDate = formatDay(lastDate);
        if (controlDay(lastWaterDate)) {
            waterBalance += waterCurrBalance;

        } else {
            waterBalance = 0;
            waterBalance += waterCurrBalance;
            Date rightNowDate = new Date();
            lastDate = rightNowDate;
        }
    }


}