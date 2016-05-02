package com.polytech.multimedia_library.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public abstract class DateUtils
{
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