package com.company.contoller;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.Expenditure;
import com.company.entity.Income;
import com.company.entity.SessionUser;
import com.company.enums.Helper;
import com.company.files.WorkWithFiles;
import com.company.service.UserService;
import com.company.util.InlineKeyboardButtonUtil;
import com.company.util.KeyboardButtonConstants;
import com.company.util.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {


    public static void handleMessage(Message message, User user) {


        if (message.hasText()) {
            String text = message.getText();
            handleText(text, message, user);

        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            handleContact(contact, message, user);

        }

    }
    private static void handleContact(Contact contact, Message message, User user) {

        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (UserService.getUserByChatId(chatId) != null ||
                UserService.getUserByPhoneNumber(contact.getPhoneNumber()) != null) return;


        com.company.entity.User newUser = new com.company.entity.User(contact.getPhoneNumber(), user.getFirstName(),
                user.getLastName(), chatId, new SessionUser(0, new ArrayList<>(), new ArrayList<>()));


        Database.userList.add(newUser);
        WorkWithFiles.writeFile();



        sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());

        sendMessage.setText("<b><u<Tanlovingiz</u></b>");
        sendMessage.setParseMode(ParseMode.HTML);


        ComponentContainer.MY_BOT.sendMsg(sendMessage);

    }

    private static void handleText(String text, Message message, User user) {


        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        com.company.entity.User currentUser = UserService.getUserByChatId(chatId);

        if (text.equals("/start")) {


            if (currentUser == null) {

                sendMessage.setText("Salom\nTelefon raqamingizni jo'nating");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.getContactMenu());

            } else {
                sendMessage.setText("Salom " + user.getFirstName() + " botga  Xush kelibsiz ");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
            }

        } else if (text.equals("/menu")) {

            if (currentUser == null) return;
            sendMessage.setText("<b><u>Tanlovingiz</u></b>");
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());

        } else if (text.equals(KeyboardButtonConstants.IN_OUT)) {


            if (currentUser == null) return;

            sendMessage.setText("<b><u>Tanlovingiz</u></b>");
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getInlineMenu());

        } else if (text.equals(KeyboardButtonConstants.SHOW_BALANCE)) {

            if (currentUser == null) return;
            sendMessage.setText("Sizni hozirgi balansingiz: <b><u>" + currentUser.getSessionUser().getBalance() + "</u></b>");
            sendMessage.setParseMode(ParseMode.HTML);

        } else if (text.equals(KeyboardButtonConstants.SHOW_HISTORY)) {

            if (currentUser == null) return;

            sendMessage.setText("<b>Tanlovingiz</b>");
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getHistoryMenu());

        } else {

            if (currentUser == null) return;

            UserService.gc(currentUser);

            Expenditure expenditure = null;

            int size = currentUser.getSessionUser().getIncomeList().size();
            Income income = currentUser.getSessionUser().getIncomeList().get(currentUser.getSessionUser().getIncomeList().size() - 1);
            if (currentUser.getSessionUser().getExpenditureList().size() != 0) {
                expenditure = currentUser.getSessionUser().getExpenditureList().get(currentUser.getSessionUser().getExpenditureList().size() - 1);
            }

            System.out.println("currentUser.getSessionUser().getIncomeList() = " + currentUser.getSessionUser().getIncomeList());
            System.out.println("currentUser.getSessionUser().getExpenditureList() = " + currentUser.getSessionUser().getExpenditureList());

            if (income.isSet()) {

                income.setToWhere(text);
                income.setSet(false);
                sendMessage.setText("<b>Daromad miqdorini kiriting: </b>");
                sendMessage.setParseMode(ParseMode.HTML);


            } else if (!income.isSet() && income.getToWhere() != null && income.getAmount() == 0) {
                try {

                    double amount = Double.parseDouble(text);
                    income.setAmount(amount);

                    if(amount<0){
                        sendMessage.setText("❌❌❌ Manfiy son kiritmang");
                        income.setSet(false);
                        sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
                        return;
                    }

                    currentUser.getSessionUser().setBalance(currentUser.getSessionUser().getBalance() + amount);
                    sendMessage.setText("Muvaffaqiyatli qo'shildi");
                    income.setTime(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now()));
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());

                    WorkWithFiles.writeFile();

                    System.out.println("currentUser.getSessionUser().getIncomeList() = " + currentUser.getSessionUser().getIncomeList());


                } catch (Exception ex) {
                    sendMessage.setText("❌❌❌ Noto'g'ri kiritildi");
                    income.setSet(false);
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
                }
            } else {
                assert expenditure != null;
                if (expenditure.isSet()) {

                    expenditure.setWhere(text);
                    expenditure.setSet(false);
                    sendMessage.setText("<b>Qancha sarf etdingiz</b>");
                    sendMessage.setParseMode(ParseMode.HTML);


                } else if (!expenditure.isSet() && expenditure.getWhere() != null) {

                    try {
                        double amount = Double.parseDouble(text);


                        if(amount<0){
                            sendMessage.setText("❌❌❌ Manfiy son kiritmang");
                            income.setSet(false);
                            sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
                            return;
                        }

                        expenditure.setAmount(amount);

                        currentUser.getSessionUser().setBalance(currentUser.getSessionUser().getBalance() + (-amount));
                        sendMessage.setText("Muvaffaqiyatli saqlandi");
                        expenditure.setTime(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());

                        WorkWithFiles.writeFile();

                        System.out.println("currentUser.getSessionUser().getExpenditureList() = " + currentUser.getSessionUser().getExpenditureList());


                    } catch (Exception ex) {
                        sendMessage.setText("❌❌❌ Noto'g'ri kiritildi");
                        expenditure.setSet(false);
                        sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
                    }
                }
            }
        }

        ComponentContainer.MY_BOT.sendMsg(sendMessage);
    }


    public static void handleCallBackQuery(User user, Message message, String data) {

        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);

        com.company.entity.User currentUser = UserService.getUserByChatId(chatId);

        if (currentUser == null) return;

        UserService.gc(currentUser);

        switch (data) {
            case "_income" -> {
                sendMessage.setText("<b>Daromad</b>\nMa'lumot kiriting(masalan, qayerdan topganingiz): ");
                sendMessage.setParseMode(ParseMode.HTML);
                List<Income> incomeList = currentUser.getSessionUser()
                        .getIncomeList();
                incomeList.add(new Income(null, 0, null, incomeList.size() + 1, true));
            }


            case "_expenditure" -> {
                sendMessage.setText("<b>Xarajat</b>\nMa'lumot kiriting(masalan, nimaga sarf qilganingiz):");
                sendMessage.setParseMode(ParseMode.HTML);
                List<Expenditure> expenditureList = currentUser.getSessionUser()
                        .getExpenditureList();
                expenditureList.add(new Expenditure(null, 0, null, expenditureList.size() + 1, true));
                System.out.println("expenditureList.size() = " + expenditureList.size());
            }
            case "_by_income" -> {

                String res = "";

                for (Income income : currentUser.getSessionUser().getIncomeList()) {
                    if (income.getTime() != null)
                        res += "Ma'lumot: " + income.getToWhere() + "\n" +
                                "Miqdor: " + income.getAmount() + " \n" +
                                "Vaqt: " + income.getTime() + "\n\n";
                }

                sendMessage.setText("Tarix <b>Daromad</>");
                if (res.length() != 0)
                    sendMessage.setText(res);
                else
                    sendMessage.setText("Bo'sh");

                sendMessage.setParseMode(ParseMode.HTML);
            }
            case "_by_expenditure" -> {

                StringBuilder res = new StringBuilder();

                for (Expenditure expenditure : currentUser.getSessionUser().getExpenditureList()) {
                    if (expenditure.getTime() != null)
                        res.append("Ma'lumot: ").append(expenditure.getWhere()).append("\n").append("Miqdor: ").append(expenditure.getAmount()).append("\n").append("Vaqt: ").append(expenditure.getTime()).append("\n\n");
                }

                sendMessage.setText("Tarix  <b>Xarajat</>\"");

                if (res.length() != 0)
                    sendMessage.setText(res.toString());
                else
                    sendMessage.setText("Bo'sh");

                sendMessage.setParseMode(ParseMode.HTML);

            }
            case "_by_all" -> {

                StringBuilder res = new StringBuilder();

                for (Income income : currentUser.getSessionUser().getIncomeList()) {
                    if (income.getTime() != null)
                        res.append("Ma'lumot (DAROMAD): ").append(income.getToWhere()).append("\n").append("Miqdor: ").append(income.getAmount()).append("\n").append("Vaqt: ").append(income.getTime()).append("\n\n");
                }

                for (Expenditure expenditure : currentUser.getSessionUser().getExpenditureList()) {
                    if (expenditure.getTime() != null)
                        res.append("Ma'lumot (XARAJAT): ").append(expenditure.getWhere()).append("\n").append("Miqdor: ").append(expenditure.getAmount()).append("\n").append("Vaqt: ").append(expenditure.getTime()).append("\n\n");
                }


                sendMessage.setText("Tarix");

                if (res.length() != 0)
                    sendMessage.setText(res.toString());
                else
                    sendMessage.setText("Bo'sh");

                sendMessage.setParseMode(ParseMode.HTML);
            }
        }

        System.out.println();
        ComponentContainer.MY_BOT.sendMsg(sendMessage);
    }
}
