package com.company.entity;

import com.company.enums.Helper;
import lombok.*;

import javax.validation.constraints.NegativeOrZero;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Income {

    private String toWhere;
    private double amount;
    private String time;
    private int id;
    private boolean isSet;



}
