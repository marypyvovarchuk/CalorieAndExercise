package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Date;

public class Water extends Options {

    public int waterBalance=0;
    public Date lastDate = new Date();

    public int getReply(int  waterCurrBalance) {
       // String amount = update.getMessage().getText();
       // int waterCurrBalance = Integer.parseInt(amount);
        String lastWaterDate = formatDay(lastDate);
        if (controlDay(lastWaterDate)) {
            waterBalance += waterCurrBalance;

        } else {
            waterBalance = 0;
            waterBalance += waterCurrBalance;
            Date rightNowDate = new Date();
            lastDate = rightNowDate;
        }
        return waterBalance;
    }


}
