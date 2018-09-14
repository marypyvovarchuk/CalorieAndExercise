package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CalorieAndExercise is a Telegram bot
 * used to count calories income, water
 * balance and wasted energy.
 *
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class CalorieAndExercise extends TelegramLongPollingBot {

    public Water water = new Water();

    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage outMessage = replyOnKeyboard(update);

            try {
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    public SendMessage replyOnKeyboard(Update update) {

        SendMessage outMessage = new SendMessage();
        String messageText = update.getMessage().getText();
        outMessage.setChatId(update.getMessage().getChatId());
        outMessage.enableMarkdown(true);

        if (messageText.equals("Water")) {


             WaterAdd(update);

        }
        else {
            if (messageText.equals("Food")) {

                outMessage.setText("You wanna add food!");


            } else {
                if (messageText.equals("Exercise")) {
                    outMessage.setText("You wanna add exercise!");
                } else {
                    outMessage.setText("Hello!"); // тимчасова відповідь на натиск кнопки
                }
            }

        }

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

        return outMessage;
    }

    public void FoodAdd() {
        Food meal = new Food();

    }

    public void WaterAdd(Update update) {

            Water waterBalance = new Water();
            Message message = update.getMessage();
            String msggggg = message.getText();

        SendMessage sm = new SendMessage();
            int msgText = waterBalance.getReply(msggggg);
            sm.setText("Your water balance: " + msgText);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }



    }

    public void ExerciseAdd() {

    }


    public String getBotUsername() {
        return "CalorieAndExerciseBot";
    }

    public String getBotToken() {
        return "572301838:AAEECuKTjMdipxaryku0FHijPZMohHHjO60";
    }

}
