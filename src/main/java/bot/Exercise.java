package bot;

import java.sql.*;
import java.util.Date;

import static java.lang.StrictMath.round;


/**
 * Specifies properties of exercise object and
 * counts wasted energy.
 *
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class Exercise extends Options {

    private Date lastDate = new Date();
    private double wastedCalories = 0;
    public int userWeight;


    /**
     * Gets wasted energy update which depends on current date
     * and exercise.
     *
     * @param wastedCurrCalories - wasted energy of current day
     */
    private void getReply(double wastedCurrCalories) {

        String lastWaterDate = formatDay(lastDate);

        if (controlDay(lastWaterDate)) {
            wastedCalories += wastedCurrCalories;
        } else {
            wastedCalories = 0;
            wastedCalories += wastedCurrCalories;

            lastDate = new Date();
        }
    }


    /**
     * Wasted energy depends on user`s
     * weight.
     *
     * @return category
     */
    private String chooseWeightCategory() {

        if (userWeight < 56) {
            return "A";
        } else if (userWeight > 85) {
            return "C";
        } else
            return "B";
    }


    /**
     * Creates answer with calculated wasted energy
     *
     * @return messageText with counted wasted energy
     */
    public String showWastedEnergy() {

        return "Congrats!\nYou wasted almost: " +
                +round(wastedCalories);
    }


    /**
     * Method is used to calculated spent energy
     * wasted on specified exercise.
     *
     * @param exerciseName - Chosen type of exercise
     * @param interval     - Amount of time spent on exercise
     */
    public void addCurrExercise(String exerciseName, int interval) {

        String sqlQuery = "SELECT * FROM EXERCISES";
        String category = chooseWeightCategory();
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

                if (s.contains(exerciseName)) {
                    double calories = rs.getFloat(category);
                    calories /= 30;

                    getReply(calories * interval);
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
}

