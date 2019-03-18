package com.example.assignmentarchitecture.utils;


import android.net.ParseException;
import com.example.assignmentarchitecture.App;
import com.example.assignmentarchitecture.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * here we are handling the String binding and other string related logics
 */
public class StringUtils {
    private static String na = App.Companion.applicationContext().getString(R.string.not_applicable);


    public static double getDoubleFromString(String bargainedAmount) {
        if (bargainedAmount != null && bargainedAmount.length() > 0) {
            bargainedAmount = bargainedAmount.replace(App.Companion.applicationContext().getString(R.string.s_kd), "").trim();
            try {
                return Double.parseDouble(bargainedAmount);
            } catch (Exception e) {
                return 0;
            }
        } else return 0;
    }

    public static String getFormattedDate(String rawDateString) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = simpleDateFormat.parse(String.valueOf(rawDateString));
            String dateAndTime = new SimpleDateFormat("dd MMM, yyyy   hh:mm a", Locale.getDefault()).format(date);
            dateAndTime = dateAndTime.replace("   ", " at ");
            return dateAndTime;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return " ";
    }

    public static String getDisplayDateFromTimeStamp(String timestamp) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = simpleDateFormat.parse(String.valueOf(timestamp));
            String dateAndTime = new SimpleDateFormat("dd MMM, yyyy   hh:mm a", Locale.getDefault()).format(date);
            dateAndTime = dateAndTime.replace("   ", "   Time: ");
            return dateAndTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";


        /*DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse("2018-04-10T04:00:00.000Z", inputFormatter);
        String formattedDate = outputFormatter.format(date)*/
    }
}
