package com.ramapps.ramplaces.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ramsheed.A on 11/03/2018.
 * Application Functions
 */
public class FunctionUtils {

    //Get the current date time
    public static String getDateTime() {
        try {
            DateFormat date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            return date.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "20181103";
    }
}
