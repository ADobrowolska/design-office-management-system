package com.designofficems.designofficemanagementsystem.util;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateTimeUtils {

    public static LocalDate toLocalDate(Instant date) {
       return LocalDate.ofInstant(date, ZoneId.systemDefault());
    }

    public static Instant toInstant(LocalDate date) {
        return date.atStartOfDay().toInstant(ZoneOffset.UTC);
    }



}
