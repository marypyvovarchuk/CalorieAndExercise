package bot;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Exercise extends Options {

    public double wastedCalories = 0;
    public Date lastDate = new Date();

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
     * Method is used to create List with all the name of
     * Exercises from EXERCISES table
     *
     * @return names - List with exercises` names
     */
    public static List<String> listWithNames() {

        Connection connection;
        List<String> names = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/caedb?autoReconnect=true&useSSL=false",
                    "root",
                    "230373hri");

            String sqlQuery = "SELECT * FROM EXERCISES";

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                String s = rs.getString("NAME");
                names.add(s);

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

        return names;
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
                    "jdbc:mysql://localhost:3306/caedb?autoReconnect=true&useSSL=false",
                    "root",
                    "230373hri");

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                String s = rs.getString("NAME");

                if (s.equals(exerName)) {

                    double calories = rs.getFloat("CALORIES");
                    System.out.println(s + "   " + calories);

                    getReply(calories); // чи правильний порядок параметрів

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

