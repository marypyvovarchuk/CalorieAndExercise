package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * CalorieAndExercise is a Telegram bot
 * used to count calories income, water
 * balance and wasted energy.
 *
 * @author  Mary Pyvovarchuk
 * @version 1.0
 * @since   2018-09-14
 */

public class Controller {


    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botAPI = new TelegramBotsApi();
        // for bot registration
        CalorieAndExercise botCAE = new CalorieAndExercise();
        try {
            botAPI.registerBot(botCAE);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
