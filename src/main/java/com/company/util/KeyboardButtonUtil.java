package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class KeyboardButtonUtil {


    public static ReplyKeyboard getContactMenu() {

        KeyboardButton button = new KeyboardButton("Send contact number");
        button.setRequestContact(true);

        KeyboardRow row = new KeyboardRow(List.of(button));

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(List.of(row));
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        markup.setOneTimeKeyboard(true);


        return markup;
    }

    public static ReplyKeyboard getBaseMenu() {

        KeyboardButton income = new KeyboardButton(KeyboardButtonConstants.IN_OUT);
        KeyboardButton showBalance = new KeyboardButton(KeyboardButtonConstants.SHOW_BALANCE);
        KeyboardButton showHistory = new KeyboardButton(KeyboardButtonConstants.SHOW_HISTORY);

        KeyboardRow row1 = new KeyboardRow(List.of(income));
        KeyboardRow row2 = new KeyboardRow(List.of(showBalance, showHistory));

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(List.of(row1, row2));

        markup.setSelective(true);
        markup.setResizeKeyboard(true);



        return markup;
    }


}
