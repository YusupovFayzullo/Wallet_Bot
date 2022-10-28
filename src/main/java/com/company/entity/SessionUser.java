package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




@NoArgsConstructor
@Getter
@Setter

public class    SessionUser {

    private double balance;
    private List<Expenditure> expenditureList;
    private List<Income> incomeList;

    public SessionUser(double balance, List<Expenditure> expenditureList, List<Income> incomeList) {
        this.balance = balance;
        this.expenditureList = expenditureList;
        this.incomeList = incomeList;

        Collections.fill(incomeList,null);
        Collections.fill(expenditureList,null);
    }
}
