package com.appt.arrkstarwars.detail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TextHelper {

    static String formatHeight(double heightInCm) {
        return Double.toString(heightInCm / 100);
    }

    static String formatDate(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.UK);
        return dt.format(date);
    }
}
