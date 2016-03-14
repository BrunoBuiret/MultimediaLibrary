package com.polytech.multimedia_library.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author bruno
 */
public abstract class DateUtils
{
    /**
     * 
     */
    protected static SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 
     */
    protected static SimpleDateFormat formFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * 
     * @param source
     * @return
     * @throws ParseException 
     */
    public static Date parseFormDate(String source)
    throws ParseException
    {
        return DateUtils.formFormat.parse(source);
    }
    
    /**
     * 
     * @param source
     * @return
     * @throws ParseException 
     */
    public static Date parseSqlDate(String source)
    throws ParseException
    {
        return DateUtils.sqlFormat.parse(source);
    }
    
    /**
     * 
     * @param source
     * @return 
     */
    public static String toSqlDate(Date source)
    {
        return DateUtils.sqlFormat.format(source);
    }
    
    /**
     * 
     * @return 
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
     * 
     * @param dateStart The start point.
     * @param dateEnd The end point.
     * @return 
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
