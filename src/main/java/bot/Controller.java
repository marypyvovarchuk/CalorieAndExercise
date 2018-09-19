package bot;

import java.sql.*;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.sql.StatementEventListener;

/**
 * Initialize and register botAPI
 *
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class Controller {


    public static void main(String[] args) {
        
        Connection connecion ;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/caedb?autoReconnect=true&useSSL=false";
            connecion = DriverManager.getConnection(url, "root", "230373hri");


            System.out.println("[OUTPUT FROM SELECT]");
            String query = "SELECT * FROM FRUITS";
            try
            {
                Statement st = connecion.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next())
                {
                    String s = rs.getString("NAME");
                    double n = rs.getFloat("CALORIES");
                    System.out.println(s + "   " + n);
                }
            }
            catch (SQLException ex)
            {
                System.err.println(ex.getMessage());
            }



            connecion.close();
        }
        catch (ClassNotFoundException ex) {System.err.println(ex.getMessage());}
        catch (IllegalAccessException ex) {System.err.println(ex.getMessage());}
        catch (InstantiationException ex) {System.err.println(ex.getMessage());}
        catch (SQLException ex)           {System.err.println(ex.getMessage());}
    }







        /*
        ApiContextInitializer.init();
        TelegramBotsApi botAPI = new TelegramBotsApi();
        // for bot registration
        CalorieAndExercise botCAE1 = new CalorieAndExercise();

        try {
            botAPI.registerBot(botCAE1);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        */
    }


