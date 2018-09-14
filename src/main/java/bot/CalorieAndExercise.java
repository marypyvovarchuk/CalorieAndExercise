package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * CalorieAndExercise is a Telegram bot
 * used to count calories income, water
 * balance and wasted energy.
 *
 * @author  Mary Pyvovarchuk
 * @version 1.0
 * @since   2018-09-14
 */

public class CalorieAndExercise extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage outMessage = new SendMessage();

            outMessage.setChatId(update.getMessage().getChatId());
            outMessage.enableMarkdown(true);
            outMessage.setText("It was start"); // тимчасова відповідь на натиск кнопки

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

            outMessage.setReplyMarkup(replyKeyboardMarkup);

            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboard = new ArrayList<>();

            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("Food"));


            KeyboardRow keyboardSecondRow = new KeyboardRow();
            keyboardSecondRow.add(new KeyboardButton("Exercise"));


            KeyboardRow keyboardThirdRow = new KeyboardRow();
            keyboardThirdRow.add(new KeyboardButton("Water"));

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);

            replyKeyboardMarkup.setKeyboard(keyboard);

            outMessage.setReplyMarkup(replyKeyboardMarkup);

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
