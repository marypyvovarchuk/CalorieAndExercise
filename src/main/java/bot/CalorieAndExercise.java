package bot;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
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

import static java.lang.Math.toIntExact;

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

        // SendMessage outMessage = new SendMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            //  keyBoardStart(update);

            SendMessage outMessage = replyOnKeyboard(update);


            try {
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            // Set variables

            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();






           // if (call_data.equals("100")) {
                int amount = countWater (call_data);
                        //water.getReply(100);

                String answer = "Your water balance:\n" + amount;
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(answer);
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();

                }
                }


        }



    public int countWater (String call_data) {
        int amount=0;

        if (call_data.equals("100")) {
            amount = water.getReply(100);
            return amount;
        } else {
            if (call_data.equals("200")) {
                amount = water.getReply(200);
                return amount;

            } else {
                if (call_data.equals("250")) {
                    amount = water.getReply(250);
                    return amount;
                }
                else {
                    if (call_data.equals("500")) {
                        amount = water.getReply(500);
                        return amount;
                    }
                    else {
                        return 0;
                    }
                }


            }


        }


        //return 0;

    }


    public SendMessage replyOnKeyboard(Update update) {

        SendMessage outMessage = new SendMessage();


        String messageText = update.getMessage().getText();
        outMessage.setChatId(update.getMessage().getChatId());



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


        if (messageText.equals("Water")) {

            outMessage.setText("You wanna add food!");
            WaterAdd(update, outMessage);


        } else {
            if (messageText.equals("Coffee")) {

                outMessage.setText("You wanna add Coffee!");
                WaterAdd(update, outMessage);


            } else {
                if (messageText.equals("Tea")) {
                    outMessage.setText("You wanna add tea!");
                    WaterAdd(update, outMessage);
                } else {
                    outMessage.setText("Hello!"); // тимчасова відповідь на натиск кнопки
                }
            }

        }


        return outMessage;
    }


    public void keyBoardStart(Update update) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        SendMessage outMessage = new SendMessage();
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

    }


    public void FoodChoose() {
        Food meal = new Food();

    }


    public void FoodWeight() {
        Food meal = new Food();

    }


    public void WaterAdd(Update update, SendMessage outMessage) {


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("100 мл").setCallbackData("100"));
        rowInline.add(new InlineKeyboardButton().setText("200 мл").setCallbackData("200"));
        rowInline.add(new InlineKeyboardButton().setText("250 мл").setCallbackData("250"));
        rowInline.add(new InlineKeyboardButton().setText("500 мл").setCallbackData("500"));
        // Set the keyboard to the markup
        // rowsInline.add(rowInline);
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        outMessage.setReplyMarkup(markupInline);

        outMessage.enableMarkdown(true);


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
