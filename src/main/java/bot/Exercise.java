package bot;

import java.sql.*;
import java.util.Date;

import static java.lang.StrictMath.round;

public class Exercise extends Options {

    private double wastedCalories = 0;
    private Date lastDate = new Date();
    public int userWeight;

    public void getReply(double wastedCurrCalories) {

        String lastWaterDate = formatDay(lastDate);

        if (controlDay(lastWaterDate)) {
            wastedCalories += wastedCurrCalories;
        } else {

            wastedCalories = 0;
            wastedCalories += wastedCurrCalories;
            Date rightNowDate = new Date();
            lastDate = rightNowDate;
        }
    }


    /**
     * Method is used to calculated spent energy
     * wasted on specified exercise.
     *
     * @param exerName - Chosen type of exercise
     */
    public void addCurrExercise(String exerName) {

        String sqlQuery = "SELECT * FROM EXERCISES";
        Connection connection;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    Properties.URLDatabase,
                    Properties.USER,
                    Properties.URLPassword);

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            String category = chooseWeightCategory();

            while (rs.next()) {

                String s = rs.getString("NAME");
                if (s.equals(exerName)) {

                    double calories = rs.getFloat(category);
                    System.out.println(s + "   " + calories);

                    getReply(calories);
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


    private String chooseWeightCategory() {

        if (userWeight < 56) {
            return "A";
        } else if (userWeight > 85) {
            return "C";
        } else
            return "B";
    }


    public String showWastedEnergy() {
        String balance = "Congrats!\nYou wasted almost: " +
                + round(wastedCalories);
        return balance;
    }


    public void addCurrMealV2(String exerciseName, int interval) {

        String sqlQuery = "SELECT * FROM EXERCISE";
        String category = chooseWeightCategory();

        Connection connection;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    Properties.URLDatabase,
                    Properties.USER,
                    Properties.URLPassword);

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);

            while (rs.next()) {

                String s = rs.getString("NAME");

                if (s.equals(exerciseName)) {

                    // for 30ty minutes
                    // choose calories range which depends on weight
                    double calories = rs.getFloat(category);
                    // for one minute
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

