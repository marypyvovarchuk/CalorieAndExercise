package bot;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Food extends Options {

    public double calorieBalance = 0;
    public double proteinBalance = 0;
    public double fatBalance = 0;
    public double carbBalance = 0;


    public Date lastDate = new Date();

    public void getReply(double calorieCurrBalance,
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


            Date rightNowDate = new Date();
            lastDate = rightNowDate;
        }
    }


    /**
     * Method is used to adjust calories and PCF balance
     *
     * @param tableName - Name of Table from Database
     * @param mealName  - Chosen meal
     */
    public void addCurrMeal(String mealName, String tableName) {

        String sqlQuery = "SELECT * FROM " + tableName;
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

                if (s.equals(mealName)) {

                    double calories = rs.getFloat("CALORIES");
                    double proteins = rs.getFloat("PROTEINS");
                    double fats = rs.getFloat("FATS");
                    double carbs = rs.getFloat("CARBS");
                    System.out.println(s + "   " + calories);

                    getReply(calories, proteins, carbs, fats); // чи правильний порядок параметрів

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


    /**
     * Method is used to create List with all the name of
     * meals from specified table
     *
     * @param tableName - Name of Table from Database
     * @return names -  List with meal names from specified
     * table.
     */
    public static List<String> listWithNames(String tableName) {

        Connection connection;
        List<String> names = new ArrayList<>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/caedb?autoReconnect=true&useSSL=false",
                    "root",
                    "230373hri");

            String sqlQuery = "SELECT * FROM " + tableName;

            for (Tables name : Tables.values()) {
                if (name.toString().equals(tableName)) {

                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(sqlQuery);
                    while (rs.next()) {
                        String s = rs.getString("NAME");
                        names.add(s);
                    }
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

        return names;
    }
}
