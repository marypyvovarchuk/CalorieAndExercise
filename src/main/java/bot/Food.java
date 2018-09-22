package bot;

import java.sql.*;
import java.util.Date;
import static java.lang.StrictMath.round;


/**
 * Specifies properties of food object,
 * counts calories and tracks protein, carbs and
 * fat balance.
 *
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class Food extends Options {

    private double calorieBalance = 0;
    private double proteinBalance = 0;
    private double fatBalance = 0;
    private double carbBalance = 0;

    private Date lastDate = new Date();

    private void getReply(double calorieCurrBalance,
                         double proteinCurrBalance,
                         double carbCurrBalance,
                         double fatCurrBalance) {

        String lastWaterDate = formatDay(lastDate);

        if (controlDay(lastWaterDate)) {
            calorieBalance += calorieCurrBalance;
            proteinBalance += proteinCurrBalance;
            fatBalance += fatCurrBalance;
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

            lastDate = new Date();
        }
    }


    /**
     * Method is used to adjust calories and PCF balance
     *
     * @param mealName - Chosen meal
     * @param amount   - Weight of chosen meal
     */
    public void addCurrMeal(String mealName, int amount) {

        String sqlQuery = "SELECT * FROM FRUITS";
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    Properties.URLDatabase,
                    Properties.URLUser,
                    Properties.URLPassword);

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);

            while (rs.next()) {
                String s = rs.getString("NAME");

                if (s.equals(mealName)) {
                    double calories = rs.getFloat("CALORIES");
                    double proteins = rs.getFloat("PROTEINS");
                    double fats = rs.getFloat("FATS");
                    double carbs = rs.getFloat("CARBS");

                    calories /= 100;
                    proteins /= 100;
                    fats /= 100;
                    carbs /= 100;

                    getReply(calories * amount,
                            proteins * amount,
                            carbs * amount,
                            fats * amount);
                }
            }
            connection.close();
        } catch (SQLException c) {
            c.printStackTrace();
        } catch (ClassNotFoundException b) {
            b.printStackTrace();
        } catch (IllegalAccessException b) {
            b.printStackTrace();
        } catch (InstantiationException b) {
            b.printStackTrace();
        }
    }


    public String showFoodBalance() {

        return "Calories: " + round(calorieBalance) + "\n" +
                "Protein: " + round(proteinBalance) + "\n" +
                "Carbs: " + round(carbBalance) + "\n" +
                "Fats: " + round(fatBalance);
    }
}
