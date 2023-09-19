package com.bardiademon.Jjson;

import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws ParseException, JjsonException {

        final JjsonObject jjsonObject = new JjsonObject()
                .put("id", "bardiademon")
                .put("name", "Bardia Namjoo")
                .put("email", "bardiademon@gmail.com")
                .put("birth", 25062555) // 2555 => 1996
                .put("programmer", true);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
        LocalDateTime birth = LocalDateTime.ofInstant(simpleDateFormat.parse(jjsonObject.asString("birth")).toInstant(), ZoneId.of("GMT+03:30"));
        final String yyyy = birth.format(DateTimeFormatter.ofPattern("yyyy"));
        System.out.println("Iranian years = " + yyyy);


        final JjsonArray jjsonArray = JjsonArray.ofString("""
                ["bardiademon","Bardia Namjoo","bardiademon@gmail.com",25062555,true]
                """);

        System.out.println("Encode = " + jjsonArray.encode());

    }
}