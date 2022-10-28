package com.company.entity;

import com.company.enums.Helper;
import lombok.*;

import javax.ws.rs.HEAD;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Expenditure {

    private String where;
    private double amount;
    private String time;
    private int id;
    private boolean isSet;

}
