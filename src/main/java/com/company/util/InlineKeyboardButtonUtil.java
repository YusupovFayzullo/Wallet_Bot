package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class InlineKeyboardButtonUtil {


    public static ReplyKeyboard getInlineMenu() {


        InlineKeyboardButton button1 = new InlineKeyboardButton(KeyboardButtonConstants.writeIncome);
        button1.setCallbackData("_income");

        InlineKeyboardButton button2 = new InlineKeyboardButton(KeyboardButtonConstants.writeExpenditure);
        button2.setCallbackData("_expenditure");

        List<InlineKeyboardButton> row = List.of(button1, button2);
        List<List<InlineKeyboardButton>> rowList = List.of(row);

        return new InlineKeyboardMarkup(rowList);

    }

    public static ReplyKeyboard getHistoryMenu() {


        InlineKeyboardButton button1 = new InlineKeyboardButton("DAROMAD");
        button1.setCallbackData("_by_income");

        InlineKeyboardButton button2 = new InlineKeyboardButton("XARAJAT");
        button2.setCallbackData("_by_expenditure");

        InlineKeyboardButton button3 = new InlineKeyboardButton("BARCHASI");
        button3.setCallbackData("_by_all");



        List<InlineKeyboardButton> row = List.of(button1, button2);
        List<List<InlineKeyboardButton>> rowList = List.of(row,List.of(button3));

        return new InlineKeyboardMarkup(rowList);


    }
}
