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
 * @author Mary Pyvovarchuk
 * @version 1.0
 * @since 2018-09-14
 */

public class CalorieAndExercise extends TelegramLongPollingBot {

    private Water water = new Water();
    final String GREETINGS = "";
    public Exercise currExerciseBalance = new Exercise();
    public String storedNameOfExercise;
    public Food currMealBalance = new Food();
    public String storedNameOfMeal = "NO MEAL";
    public String storedNameOfTable = "NO TABLE";
    public int weight;
    public String oldMessage;

    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();
            if (message.equals("/start")) {
                helloBot(update);
            } else {
                SendMessage outMessage = replyOnKeyboard(update);
                try {
                    execute(outMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void helloBot(Update update) {

        SendMessage outMessage = new SendMessage();
        String greetings = "Hello and welcome!\nPlease enter your weight to" +
                "make calculations more correct.\nBut honestly :) ";
        outMessage.setChatId(update.getMessage().getChatId());
        outMessage.setText(greetings);
        try {
            execute(outMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    /**
     * Replies on user input and responds when
     * buttons have been pressed.
     *
     * @param update current update
     * @return outMessage with instruction which is to be passed
     * to onUpdateReceived method
     */
    private SendMessage replyOnKeyboard(Update update) {

        SendMessage outMessage = new SendMessage();

        String messageText = update.getMessage().getText();
        outMessage.setChatId(update.getMessage().getChatId());

        // Range of water amount: [1;500] ml
        String regexWater = "[1-9]|[1-8][0-9]|9[0-9]|[1-4][0-9]{2}|500";

        // Range of exercise interval: [1;120] min
        String regexExe = "([1-9]|[1-8][0-9]|9[0-9]|1[01][0-9]|120)";

        // Range of meal weight: [1;1000] g
        String regexFood = "([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|1000)";


        String regexWeight = "([2-8][0-9]|9[0-9]|1[0-9]{2}|200)";

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

        if (messageText.equals("FOOD")) {
            storedNameOfTable = "FOOD";
            outMessage.setText("Enter name of meal:");
            return outMessage;
        } else if (messageText.equals("WATER")) {
            outMessage.setText("Enter amount: \nExample: 100 ml");
        } else if (messageText.equals("EXERCISE")) {
            //outMessage.setText("weight" + weight);
            // return outMessage;
            storedNameOfTable = "EXERCISE";
            outMessage.setText("Enter name of exercise:");
            return outMessage;
        }
        if (messageText.contains("ml")) {
            // check if matches regex
            water.getReply(Integer.parseInt(replaceMl(messageText)));
            int amount = water.waterBalance;

            String answer = "Your water balance:\n" + amount;
            outMessage.setText(answer);

        } else {
            if (messageText.contains("min")) {
                outMessage.setText("Enter weight: \nExample: 100 g");

            } else {
                if (messageText.contains(" g")) {
                    int amount = Integer.parseInt(replaceG(messageText));
                    currMealBalance.addCurrMeal(storedNameOfMeal, amount);

                    outMessage.setText(currMealBalance.showFoodBalance());
                } else {
                    if (storedNameOfTable.equals("FOOD")) {
                        outMessage.setText("Enter weight. \nExample: 100 g");
                        storedNameOfMeal = messageText;
                    }
                    if (storedNameOfTable.equals("EXERCISE")) {
                        outMessage.setText("Enter interval. \nExample: 10 min");
                        storedNameOfExercise = messageText;
                    } else if (messageText.contains("kg")) {
                        weight = Integer.parseInt(replaceKG(messageText));
                        outMessage.setText("Thank you!\nNow choose one option you want to add.");
                        return outMessage;
                    } else
                        outMessage.setText("Please input correctly!");


                }
            }
        }

        return outMessage;
    }


    public String replacemetn(List<String> list) {
        String output = list.toString();
        String output1 = output.replace(",", "\n");
        String output2 = output1.replace("[", "");
        String output3 = output2.replace("]", "");

        return output3;

    }


    /**
     * In case user added input as "~10~ min" to specify time
     * spent working out, this method indicates correct Int value with time.
     * Return String param used to calculate wasted energy.
     * On other hand in case input would not be like "~10~ min"
     * method will return the same String param.
     *
     * @param reg user input
     * @return result String line with replaced substring containing "  min"
     */
    public String replaceMin(String reg) {

        if (reg.contains("ml")) {
            String result = reg.replace(" min", "");
            return result;
        } else
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
     * @return result String line with replaced substring containing "  ml"
     */
    public String replaceMl(String reg) {

        if (reg.contains("ml")) {

            String result = reg.replace(" ml", "");
            return result;
        } else
            return reg;
    }


    /**
     * Method is used whereas user added input as "~100~ g" to specify amount
     * of meal.
     * Return String param whis os used used to calculate calories,
     * carbs, fats and protein.
     * On other hand in case input would not be like "~100~ g"
     * method will return the same String param.
     *
     * @param reg user input
     * @return result String line with replaced substring containing "  g"
     */
    public String replaceG(String reg) {

        if (reg.contains("g")) {

            String result = reg.replace(" g", "");
            return result;
        } else
            return reg;
    }


    public String replaceKG(String reg) {

        if (reg.contains("kg")) {

            String result = reg.replace(" kg", "");
            return result;
        } else
            return reg;
    }


    public String getBotUsername() {
        return "CalorieAndExerciseBot";
    }


    public String getBotToken() {
        return "572301838:AAEECuKTjMdipxaryku0FHijPZMohHHjO60";
    }
}
