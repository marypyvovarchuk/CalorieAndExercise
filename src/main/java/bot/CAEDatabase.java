package bot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CAEDatabase {

    Food currMealBalance = new Food();
    Exercise currExerciseBalance = new Exercise();

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


    // для вибору користувачем продукту || вправи
    public static List<String> namesOfMealAndExer(String tableName) {

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

                    currMealBalance.getReply(calories, proteins, carbs, fats); // чи правильний порядок параметрів

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

                    currExerciseBalance.getReply(calories); // правильний порядок параметрів?
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