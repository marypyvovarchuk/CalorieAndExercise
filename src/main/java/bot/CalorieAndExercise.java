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

            if (inMessage.equals("/apple")) {

                //System.out.println(update.getMessage().getFrom().getFirstName());

                outMessage.setText("Banana:\n100 calories");
            }

            if (inMessage.equals("/banana")) {

                // System.out.println(update.getMessage().getFrom().getLastName());
                outMessage.setText("Apple:\n52 calories");
            }

            outMessage.setChatId(update.getMessage().getChatId());

            try {
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }
    }


    //@Override
    public String getBotUsername () {
        return "CalorieAndExerciseBot";
    }

    public String getBotToken () {
        return "572301838:AAEECuKTjMdipxaryku0FHijPZMohHHjO60";
    }

}
