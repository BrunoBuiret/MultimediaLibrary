package com.polytech.multimedia_library.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public abstract class DateUtils
{
    /**
     * The date format used by SQL.
     */
    protected static SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * The date format used by the forms.
     */
    protected static SimpleDateFormat formFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Parses a string with the form format to get a date.
     * 
     * @param source The string to parse.
     * @return The corresponding date.
     * @throws ParseException If a parsing error happens.
     */
    public static Date parseFormDate(String source)
    throws ParseException
    {
        return DateUtils.formFormat.parse(source);
    }
    
    /**
     * Parses a string with the sql format to get a date.
     * 
     * @param source The string to parse.
     * @return The corresponding date.
     * @throws ParseException If a parsing error happens.
     */
    public static Date parseSqlDate(String source)
    throws ParseException
    {
        return DateUtils.sqlFormat.parse(source);
    }
    
    /**
     * Formats a date to a SQL date string format.
     * 
     * @param source The date to format.
     * @return The corresponding string.
     */
    public static String toSqlDate(Date source)
    {
        return DateUtils.sqlFormat.format(source);
    }
    
    /**
     * Gets a <code>Date</code> instance of today at midnight.
     * 
     * @return Today's date instance.
     */
    public static Date getToday()
    {
        Calendar calendar = Calendar.getInstance();
            
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        
        return calendar.getTime();
    }
    
    /**
     * Gets every date between two points.
     * 
     * @param dateStart The start point.
     * @param dateEnd The end point.
     * @return The list of dates.
     * @see http://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java#answer-2893790
     */
    public static List<Date> getDaysBetween(Date dateStart, Date dateEnd)
    {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        
        // Add one day so as to include the last one
        calendar.setTime(dateEnd);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dateEnd = calendar.getTime();
        
        // Go from the start to the end
        calendar.setTime(dateStart);
        
        while(calendar.getTime().before(dateEnd))
        {
            Date dateCurrent = calendar.getTime();
            dates.add(dateCurrent);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return dates;
    }
}
