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
final String GREETINGS = "";

    public void onUpdateReceived(Update update) {

        // SendMessage outMessage = new SendMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            //  keyBoardStart(update);
            //SendMessage outMessage = new SendMessage();

            String message = update.getMessage().getText();
           if( message.equals("/start")) {

                helloBot(update);

           }
else {

               SendMessage outMessage = replyOnKeyboard(update);
               try {
                   execute(outMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }

           }


        } else if (update.hasCallbackQuery()) {

            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();



            int amount = countWater(call_data);

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


    public void helloBot (Update update) {


            SendMessage outMessage = new SendMessage();


            //String messageText = update.getMessage().getText();
            outMessage.setChatId(update.getMessage().getChatId());


            outMessage.setText(GREETINGS);// instructions tp press calculate





    }


    public int countWater(String call_data) {
        int amount = 0;

        if (call_data.equals("100")) {
            water.getReply(100);
            amount = water.waterBalance;
            return amount;
        } else {
            if (call_data.equals("200")) {
                water.getReply(200);
                amount = water.waterBalance;
                return amount;

            } else {
                if (call_data.equals("250")) {
                    water.getReply(250);
                    amount = water.waterBalance;
                    return amount;
                } else {
                    if (call_data.equals("500")) {
                        water.getReply(500);
                        amount = water.waterBalance;
                        return amount;
                    } else {
                        return 0;
                    }
                }


            }


        }


    }


    public SendMessage replyOnKeyboard(Update update) {

        SendMessage outMessage = new SendMessage();





        String messageText = update.getMessage().getText();
        outMessage.setChatId(update.getMessage().getChatId());

        messageText = replaceIt(messageText);

        String regexWater = "[1-9]|[1-8][0-9]|9[0-9]|[1-4][0-9]{2}|500";
       // String regexExe = "[1-9]|[1-8][0-9]|9[0-9]|[1-4][0-9]{2}|500";

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        outMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Water"));


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Exercise"));


        //KeyboardRow keyboardThirdRow = new KeyboardRow();
       // keyboardThirdRow.add(new KeyboardButton("Water"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
      //  keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        outMessage.setReplyMarkup(replyKeyboardMarkup);


        if (messageText.equals("Water") || messageText.equals("/water")) {

            outMessage.setText("Enter amount: \nExample: 100 ml");
         //  WaterAdd(update, outMessage);



                } else {
            if (messageText.equals("Exercise") || messageText.equals("/exercise")) {

                WaterAdd(outMessage);

            }


            else {
                if (messageText.matches(regexWater)) {
                    System.out.print("match\n"); //

                    water.getReply(Integer.parseInt(messageText));
                    int amount = water.waterBalance;


                    String answer = "Your water balance:\n" + amount;
                    outMessage.setText(answer);




                } else {
                    outMessage.setText("Hello!");
                }
                // тимчасова відповідь на натиск кнопки
            }
        }


        return outMessage;
    }


    public String replaceIt (String reg  ) {

        if (reg.contains("ml")) {

        String result= reg.replace(" ml", "");
            return result;
        }

        else
            return reg;

    }



   /* public void FoodWeight (SendMessage outMessage) {

        water.getReply(Integer.parseInt(messageText));
        int amount = water.waterBalance;


        String answer = "Your water balance:\n" + amount;
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(answer);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }


    } */


    public void keyBoardStart(Update update) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        SendMessage outMessage = new SendMessage();
        outMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Water"));


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Exercise"));


      //..  KeyboardRow keyboardThirdRow = new KeyboardRow();
       // keyboardThirdRow.add(new KeyboardButton("Water"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
      //  keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        outMessage.setReplyMarkup(replyKeyboardMarkup);

    }





    public void WaterAdd( SendMessage outMessage) {


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("5 min").setCallbackData("5"));
        rowInline.add(new InlineKeyboardButton().setText("10 min").setCallbackData("10"));
        rowInline.add(new InlineKeyboardButton().setText("15 min").setCallbackData("15"));
        rowInline.add(new InlineKeyboardButton().setText("20 min").setCallbackData("20"));
        rowInline.add(new InlineKeyboardButton().setText("30 min").setCallbackData("30"));
        // Set the keyboard to the markup
        // rowsInline.add(rowInline);
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        outMessage.setReplyMarkup(markupInline);

        outMessage.enableMarkdown(true);


    }




    public void WaterCount (SendMessage outMessage) {


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("100 мл").setCallbackData("100"));
        rowInline.add(new InlineKeyboardButton().setText("200 мл").setCallbackData("200"));
        rowInline.add(new InlineKeyboardButton().setText("250 мл").setCallbackData("250"));
        rowInline.add(new InlineKeyboardButton().setText("500 мл").setCallbackData("500"));

        rowsInline.add(rowInline);

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
