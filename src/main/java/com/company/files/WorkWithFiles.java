package com.company.files;

import com.company.db.Database;
import com.company.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFiles {

    public static String BASE_FILE = "src/main/resources/docs";
    public static Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public static File usersFile=new File(BASE_FILE,"users.json");

    public static void readFile() {


        if(!usersFile.exists()||usersFile.length()==0) return;

        try {
            List list = GSON.fromJson(new BufferedReader(new FileReader(BASE_FILE + "/users.json")), new TypeToken<ArrayList<User>>() {
            }.getType());
            Database.userList.clear();
            Database.userList.addAll(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile() {
        try (PrintWriter writer = new PrintWriter(new File(BASE_FILE, "users.json"))) {
            writer.write(GSON.toJson(Database.userList));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
