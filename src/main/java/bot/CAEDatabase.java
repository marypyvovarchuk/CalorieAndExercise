package bot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CAEDatabase {

    public static void getConnection(String tableName) {

        Connection connection;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/caedb?autoReconnect=true&useSSL=false",
                    "root",
                    "230373hri");


            String sqlQuery = "SELECT * FROM " + tableName;

            Tables table = Tables.valueOf(tableName);

            switch (table) {

                case FRUITS:

                    break;

            }


            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                String s = rs.getString("NAME");
                double n = rs.getFloat("CALORIES");
                System.out.println(s + "   " + n);

            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException a) {
            a.printStackTrace();
        } catch (IllegalAccessException b) {
            b.printStackTrace();
        } catch (SQLException c) {
            c.printStackTrace();
        }


    }

    public static List<String> outputNames(String tableName, Connection connection) {
        List<String> names = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + tableName;


        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                String s = rs.getString("NAME");
                double n = rs.getFloat("CALORIES");
                System.out.println(s + "   " + n);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }


}