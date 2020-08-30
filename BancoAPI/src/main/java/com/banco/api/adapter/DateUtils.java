package com.banco.api.adapter;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static DateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

    public static Date parse(String date) {
        try {
            return dateFormatter.parse(date);
        } catch (ParseException ex) {
            LOGGER.error(ex.getLocalizedMessage());
            throw new RuntimeException(ex);
        }
    }

    public static String format(Date date) {
        return dateFormatter.format(date);
    }

    public static DateTime getDateTime() {
        return DateTime.now(); //timezone?
    }
}
