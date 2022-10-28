package com.company.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {

    private String phone;
    private String firstName;
    private String lastName;
    private String chatId;
    private SessionUser sessionUser;
}
