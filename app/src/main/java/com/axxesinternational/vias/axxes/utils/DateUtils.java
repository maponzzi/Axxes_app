package com.axxesinternational.vias.axxes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import android.util.Log;

public class DateUtils {

    public static String setDateFormat(String date){

        String outDate = null;
        //TimeZone tzUTC = TimeZone.getTimeZone("UTC");
        //Log.e("TimeZone:", tzUTC.toString());

        String[] dateArray = date.split("T");
        String onlyDate = dateArray[0];
        //Log.e("Solo Fecha:", onlyDate);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        //inputFormat.setTimeZone(tzUTC);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            outDate = outputFormat.format(inputFormat.parse(onlyDate));
        } catch (ParseException e) {
            Log.e("Error parser", Log.getStackTraceString(e));
        }

        return outDate;

    }

}
