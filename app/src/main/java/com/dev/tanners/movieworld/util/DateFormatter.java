package com.dev.tanners.movieworld.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String formatDate(String mDate, String originalPattern, String newPattern) {
        DateFormat mDateFormatStart = new SimpleDateFormat(originalPattern);
        Date mDataStart = null;
        try {
            mDataStart = mDateFormatStart.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat newDateFormat = new SimpleDateFormat(newPattern);
        return newDateFormat.format(mDataStart);
    }
}

