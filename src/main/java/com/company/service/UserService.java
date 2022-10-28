package com.company.service;

import com.company.db.Database;
import com.company.entity.Expenditure;
import com.company.entity.Income;
import com.company.entity.User;

import java.util.List;

public class UserService {
    public static User getUserByChatId(String chatId) {

        return Database.userList.stream()
                .filter(user -> user.getChatId().equals(chatId))
                .findFirst()
                .orElse(null);
    }

    public static User getUserByPhoneNumber(String phoneNumber) {

        return Database.userList.stream()
                .filter(user -> user.getPhone().equals(phoneNumber))
                .findFirst()
                .orElse(null);
    }

    public static void gc(User currentUser) {

        List<Expenditure> expenditureList = currentUser.getSessionUser().getExpenditureList();
        List<Income> incomeList = currentUser.getSessionUser().getIncomeList();

        for (int i = 0; i < expenditureList.size(); i++) {

            if (i != expenditureList.size() - 1 && expenditureList.get(i).getTime() == null)
                expenditureList.remove(expenditureList.get(i));
        }

        for (int i = 0; i < incomeList.size(); i++) {

            if (i != incomeList.size() - 1 && incomeList.get(i).getTime() == null)
                incomeList.remove(incomeList.get(i));
        }
    }
}
