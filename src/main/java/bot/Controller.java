package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


/**
 * Initialize and register botAPI
 *
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class Controller {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botAPI = new TelegramBotsApi();
        CalorieAndExercise botCAE1 = new CalorieAndExercise();

        try {
            botAPI.registerBot(botCAE1);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
