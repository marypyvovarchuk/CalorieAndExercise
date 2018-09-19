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
    public Exercise exercise = new Exercise();

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

        outMessage.setChatId(update.getMessage().getChatId());
        outMessage.setText(GREETINGS);
    }


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


        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Food"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        outMessage.setReplyMarkup(replyKeyboardMarkup);


        Menu menu = Menu.valueOf(messageText);

        switch (menu) {
            case Water:
                outMessage.setText("Enter amount: \nExample: 100 ml");
                break;
            case Exercise:
                // note: choose an exercise
                outMessage.setText("Enter interval: \nExample: 10 min");
                break;
            case Food:
                // note: choose a meal
                outMessage.setText("Enter weight: \nExample: 100 g");
                break;
            default:
                outMessage.setText("Please, text correctly!");
        }


        if (replaceMl(messageText).matches(regexWater)) {

            water.getReply(Integer.parseInt(replaceMl(messageText)));
            int amount = water.waterBalance;

            String answer = "Your water balance:\n" + amount;
            outMessage.setText(answer);

        } else {
            if (replaceMin(messageText).matches(regexExe)) {
                // to do in case user enter interval of exercises
            } else {
                if (replaceG(messageText).matches(regexFood)) {
                    // to do in case user enter meal weight
                }
            }
        }

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


    public String getBotUsername() {
        return "CalorieAndExerciseBot";
    }


    public String getBotToken() {
        return "572301838:AAEECuKTjMdipxaryku0FHijPZMohHHjO60";
    }

}
