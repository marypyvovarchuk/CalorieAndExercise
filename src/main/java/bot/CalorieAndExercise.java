package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    private Water water = new Water();
    public Exercise currExerciseBalance = new Exercise();
    private String storedNameOfExercise;
    private Food currMealBalance = new Food();
    private String storedNameOfMeal = "No meal chosen right now";
    private String storedNameOfTable = "No table chosen right now";

    /**
     * Declared abstract method that replies on
     * each user input and gives answers.
     *
     * @param update current update
     */
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage outMessage;
            String message = update.getMessage().getText();

            if (message.equals("/start")) {
                outMessage = helloBot(update);
            } else {
                outMessage = replyOnKeyboard(update);
            }

            try {
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Replies first user input - "/start" message and sets
     * ReplyOnMarkup keyboard with menu.
     *
     * @param update current update
     * @return outMessage with instruction which is to be passed
     * to onUpdateReceived method
     */
    private SendMessage helloBot(Update update) {

        SendMessage outMessage = new SendMessage();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        outMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("WATER"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("EXERCISE"));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("FOOD"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        outMessage.setReplyMarkup(replyKeyboardMarkup);

        String greetings = "Hello and welcome!\nPlease enter your weight to" +
                "make calculations more correct.\nBut honestly :) ";
        outMessage.setChatId(update.getMessage().getChatId());
        outMessage.setText(greetings);
        return outMessage;
    }


    public String foodChoosed() {
        storedNameOfTable = "FOOD";
        return "Enter name of meal:";
    }


    public String exerciseChoosed() {
        storedNameOfTable = "EXERCISE";
        return "Enter name of exercise:";
    }


    public String waterChoosed() {
        storedNameOfTable = "WATER";

        return "Enter amount: \nExample: 100 ml";
    }


    /**
     * Replies on user input and responds when
     * buttons have been pressed.
     *
     * @param update current update
     * @return outMessage with instruction which is to be passed
     * to onUpdateReceived method
     */
    public SendMessage replyOnKeyboard(Update update) {

        SendMessage outMessage = new SendMessage();

        String messageText = update.getMessage().getText();
        outMessage.setChatId(update.getMessage().getChatId());




        HashMap<Boolean, String> hash = new HashMap<>();

        hash.put(messageText.equals("/start"), foodChoosed());
         hash.put(messageText.equals("FOOD"), foodChoosed());
         hash.put(messageText.equals("EXERCISE"), exerciseChoosed());
         hash.put(messageText.equals("WATER"), waterChoosed());
         // hash.put(messageText.contains("ml"), mlEntered(messageText));
        //hash.put(messageText.contains(" g"), gEntered(messageText));
        // hash.put(messageText.contains("kg"), kgEntered(messageText));
        // hash.put(messageText.contains("min"), minEntered(messageText));


        if (hash.containsKey(true))
          for (HashMap.Entry<Boolean, String> pair : hash.entrySet()) {
                if (pair.getKey()) {
                    outMessage.setText(pair.getValue());
                    return outMessage;
                }
            }

      /*  if (messageText.equals("FOOD")) {
            storedNameOfTable = "FOOD";
            outMessage.setText("Enter name of meal:");

            return outMessage;
        } else if (messageText.equals("WATER")) {
            storedNameOfTable = "WATER";
            outMessage.setText("Enter amount: \nExample: 100 ml");

            return outMessage;
        } else if (messageText.equals("EXERCISE")) {
            storedNameOfTable = "EXERCISE";
            outMessage.setText("Enter name of exercise:");

            return outMessage;
        }*/

        if (messageText.contains("ml")) {
            water.getReply(Integer.parseInt(replaceMl(messageText)));
            int amount = water.waterBalance;
            String answer = "Your water balance:\n" + amount;
            outMessage.setText(answer);

            return outMessage;
        } else if (messageText.contains("min")) {

            int interval = Integer.parseInt(replaceMin(messageText));
            currExerciseBalance.addCurrExercise(storedNameOfExercise, interval);
            outMessage.setText(currExerciseBalance.showWastedEnergy());

            return outMessage;
        } else if (messageText.contains(" g")) {
            int amount = Integer.parseInt(replaceG(messageText));
            currMealBalance.addCurrMeal(storedNameOfMeal, amount);
            outMessage.setText(currMealBalance.showFoodBalance());

            return outMessage;
        } else if (storedNameOfTable.equals("FOOD")) {
            outMessage.setText("Enter weight. \nExample: 100 g");
            storedNameOfMeal = messageText;
            return outMessage;
        } else if (storedNameOfTable.equals("EXERCISE")) {
            storedNameOfExercise = messageText;
            outMessage.setText("Enter interval. \nExample: 10 min");

            return outMessage;
        } else if (messageText.contains("kg")) {
            int weight = Integer.parseInt(replaceKg(messageText));
            storedNameOfTable = "No table chosen right now";
            currExerciseBalance.userWeight = weight;
            outMessage.setText("Thank you!\nNow choose one option you want to add.");

            return outMessage;
        } else
            outMessage.setText("Please input correctly!");
        return outMessage;
    }


    /**
     * In case user added input as "~10~ min" to specify time
     * spent working out, this method indicates correct Int value with time.
     * Return String param used to calculate wasted energy.
     * On other hand in case input would not be like "~10~ min"
     * method will return the same String param.
     *
     * @param reg user input
     * @return result String line with numbers only
     */
    private String replaceMin(String reg) {

        if (reg.contains("min")) {
            reg = reg.replace(" min", "");
        }
        return reg;
    }


    /**
     * Method is used in case user added input as "~200~ ml" which indicates
     * current water income.
     * Return String param used to calculate water balance.
     * On other hand in case input would not be like "~200~ ml"
     * method will return the same String param.
     *
     * @param reg user input
     * @return result String line with numbers only
     */
    private String replaceMl(String reg) {

        if (reg.contains("ml")) {
            reg = reg.replace(" ml", "");
        }
        return reg;
    }


    /**
     * Method is used whereas user added input as "~100~ g" to specify amount
     * of meal.
     * Return String param which os used used to calculate calories,
     * carbs, fats and protein.
     * On other hand in case input would not be like "~100~ g"
     * method will return the same String param.
     *
     * @param reg user input
     * @return result String line with numbers only
     */
    private String replaceG(String reg) {

        if (reg.contains("g")) {
            reg = reg.replace(" g", "");
        }
        return reg;
    }


    /**
     * Method deletes from input like "~50 kg" substring
     * with letters.
     *
     * @param reg user input
     * @return result String line with numbers only
     */
    private String replaceKg(String reg) {

        if (reg.contains("kg")) {
            reg = reg.replace(" kg", "");
        }
        return reg;
    }


    public String getBotUsername() {
        return Properties.USER;
    }


    public String getBotToken() {
        return Properties.TOKEN;
    }
}