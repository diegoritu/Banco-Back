package com.banco.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static DateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

    public static boolean isValid(String dateStr) {
    	if(dateStr != null)
    	{
	        try {
	            dateFormatter.parse(dateStr);
	        } catch (ParseException ex) {
	            return false;
	        }
	        return true;
    	}
    	else {
    		return true;
    	}
    }

    public static Date parse(String date) {
        if(date != null) {
        	try {
                return dateFormatter.parse(date);
            } catch (ParseException ex) {
                LOGGER.error(ex.getLocalizedMessage());
                throw new RuntimeException(ex);
            }        	
        }
        else {
        	return null;
        }
    }

    public static String format(Date date) {
    	if(date != null) {
            return dateFormatter.format(date);   		
    	}
    	else {
    		return null;
    	}
    }

}
