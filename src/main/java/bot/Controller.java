package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Controller {


    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botAPI = new TelegramBotsApi();
        // for bot registration
        CalorieAndExercise botCAE = new CalorieAndExercise();
        try {
            botAPI.registerBot(botCAE);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
