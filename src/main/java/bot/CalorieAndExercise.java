package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class CalorieAndExercise extends TelegramLongPollingBot {


    //@Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String inMessage = update.getMessage().getText();
            SendMessage outMessage = new SendMessage();

            outMessage.setChatId(update.getMessage().getChatId());


            if (inMessage.equals("/banana")) {

                outMessage.setText("Banana:\n100 calories");
            }

            if (inMessage.equals("/apple")) {


                outMessage.setText("Apple:\n52 calories");
            }

            // Для уникнення помилки:  "Text parameter can't be empty in method: SendMessage"
            if (!
                    inMessage.equals("/apple") && !inMessage.equals("/banana")) {
                outMessage.setText("Not found!\nSorry!");
            }

            try {
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }
    }


    public String getBotUsername() {
        return "CalorieAndExerciseBot";
    }

    public String getBotToken() {
        return "572301838:AAEECuKTjMdipxaryku0FHijPZMohHHjO60";
    }

}
