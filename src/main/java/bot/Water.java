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

    int waterBalance = 0;
    private Date lastDate = new Date();


    /**
     * If last water update was not today waterBalance = 0
     * then added current water in ml. In case last water update
     * was during current day water input are added to stored
     * waterBalance.
     *
     * @param waterCurrBalance - water input od current day
     */
    public void getReply(int waterCurrBalance) {

        String lastWaterDate = formatDay(lastDate);

        if (controlDay(lastWaterDate)) {
            waterBalance += waterCurrBalance;
        } else {
            waterBalance = 0;
            waterBalance += waterCurrBalance;
            lastDate = new Date();
        }
    }
}