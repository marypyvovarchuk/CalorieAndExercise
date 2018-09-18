package bot;

import java.util.Date;

public class Food extends Options {

    public double calorieBalance = 0;
    public double proteinBalance = 0;
    public double fatBalance = 0;
    public double carbBalance = 0;


    public Date lastDate = new Date();


    public void getReply(int calorieCurrBalance,
                        int proteinCurrBalance,
                        int carbCurrBalance,
                        int fatCurrBalance) {

        Food obj = new Food();

        String lastWaterDate = formatDay(lastDate);
        if (controlDay(lastWaterDate)) {
            obj.calorieBalance += calorieCurrBalance;
            obj.proteinBalance += proteinCurrBalance;
            obj.fatBalance += fatCurrBalance;
            carbBalance += carbCurrBalance;

        } else {
            calorieBalance = 0;
            proteinBalance = 0;
            fatBalance = 0;
            carbBalance = 0;

            calorieBalance += calorieCurrBalance;
            proteinBalance += proteinCurrBalance;
            fatBalance += fatCurrBalance;
            carbBalance += carbCurrBalance;


            Date rightNowDate = new Date();
            lastDate = rightNowDate;
        }
       // return obj;
    }


}
