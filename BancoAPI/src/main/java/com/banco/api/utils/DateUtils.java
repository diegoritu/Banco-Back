package com.banco.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static final ZoneId ZONE_ID = ZoneId.of("America/Buenos_Aires");
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
    		return false;
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

    public static Date plusDays(Date date, long days) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZONE_ID).toLocalDateTime();
        LocalDateTime datePlusDays = localDateTime.plusDays(days);
        return Date.from(datePlusDays.atZone(ZONE_ID).toInstant());
    }

    public static Date atStartOfDay(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZONE_ID).toLocalDate();
        return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
    }

    public static ZoneId getZoneId() {
        return ZONE_ID;
    }
}
