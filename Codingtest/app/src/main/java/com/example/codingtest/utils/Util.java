package com.example.codingtest.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.util.DisplayMetrics;
import android.util.Log;

import com.example.codingtest.MainActivity;

public class Util {

    public static String getDateString(String date){
        int index = date.indexOf("T");
        String resultMsg = date.substring(0, index);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String dateString;
        dateString = resultMsg;
        Date commitDate = null;
        try {
            commitDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = todayDate.getTime() - commitDate.getTime();

        long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if(dayDiff == 0){

            dateString = date.substring(index+1, index+6);
            //Log.d("agile =====", resultMsg);
        } else if(dayDiff == 1){
            dateString = "Yesterday";
        } else if(dayDiff >1 && dayDiff <= 6){
            Format f = new SimpleDateFormat("EEEE");
            dateString = f.format(commitDate);
        } else{
            DateFormat dateFormatFit = new SimpleDateFormat("dd/MM/yy");
            dateString = dateFormatFit.format(commitDate);
        }

      return  dateString;
    }

}
