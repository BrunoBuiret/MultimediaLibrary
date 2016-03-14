package com.polytech.multimedia_library.entities.works.sellable;

import com.polytech.multimedia_library.entities.Adherent;
import java.util.Date;

/**
 *
 * @author bruno
 */
public class Booking
{
    /**
     * 
     */
    protected Adherent adherent;
    
    /**
     * 
     */
    protected Date date;
    
    /**
     * 
     */
    protected String state;
    
    /**
     * 
     * @param adherent
     * @param date
     * @param state 
     */
    public Booking(Adherent adherent, Date date, String state)
    {
        this.adherent = adherent;
        this.date = date;
        this.state = state;
    }

    /**
     * 
     * @return 
     */
    public Adherent getAdherent()
    {
        return adherent;
    }

    /**
     * 
     * @param adherent 
     */
    public void setAdherent(Adherent adherent)
    {
        this.adherent = adherent;
    }

    /**
     * 
     * @return 
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * 
     * @param date 
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * 
     * @return 
     */
    public String getState()
    {
        return state;
    }

    /**
     * 
     * @param state 
     */
    public void setState(String state)
    {
        this.state = state;
    }
}
