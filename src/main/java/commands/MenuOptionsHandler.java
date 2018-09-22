package commands;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class MenuOptionsHandler extends TelegramLongPollingBot {

    public final String WATER = "WATER";
    public final String FOOD = "FOOD";
    public final String EXERCISE = "EXERCISE";

    public String responce;



    @Override
    public void onUpdateReceived  ( Update update) {


    }
}
