package com.company.bot;

import com.company.container.ComponentContainer;
import com.company.contoller.MainController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {


    @Override
    public String getBotToken() {
        return ComponentContainer.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            Message message = update.getMessage();
            User user = message.getFrom();

            MainController.handleMessage(message, user);

        } else if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            User user = callbackQuery.getFrom();
            String data = callbackQuery.getData();

            MainController.handleCallBackQuery(user, message, data);
        }



    }

    @Override
    public String getBotUsername() {
        return ComponentContainer.BOT_USERNAME;
    }



    public void sendMsg(Object obj){

        try {
            if (obj instanceof SendMessage) {
                execute((SendMessage)obj);
            }
            else if (obj instanceof DeleteMessage) {
                execute((DeleteMessage)obj);
            }
            else if (obj instanceof EditMessageText) {
                execute((EditMessageText)obj);
            }
            else if (obj instanceof SendDocument) {
                execute((SendDocument) obj);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
