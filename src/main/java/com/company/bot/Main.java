package com.company.bot;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.files.WorkWithFiles;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {


        try {

            WorkWithFiles.readFile();

            System.out.println("Database.userList = " + Database.userList);


            MyBot myBot = new MyBot();
            ComponentContainer.MY_BOT = myBot;

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(myBot);

        } catch (TelegramApiException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

    }
}
