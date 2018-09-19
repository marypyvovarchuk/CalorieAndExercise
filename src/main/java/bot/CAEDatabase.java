package bot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CAEDatabase {

    Food currMeal = new Food();

    public void getConnection(String tableName) {

        Connection connection;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/caedb?autoReconnect=true&useSSL=false",
                    "root",
                    "230373hri");





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


    // для вибору користувачем продукту
    public static List<String> outputNames(String tableName, Connection connection) {
        List<String> names = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + tableName;


        for (Tables name : Tables.values()) {
            if (name.toString().equals(tableName)) {
                try {
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(sqlQuery);
                    while (rs.next()) {
                        String s = rs.getString("NAME");
                        double n = rs.getFloat("CALORIES");
                        System.out.println(s + "   " + n);
                        // System.out.println(name);
                        names.add(s);
                        //count++;

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return names;
    }


    public void addCurrMeal(String mealName, Connection connection, String tableName) {
        String sqlQuery = "SELECT * FROM " + tableName;


        try {
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

                    currMeal.getReply(calories, proteins, carbs, fats); // чи правильний порядок параметрів

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}