package com.polytech.multimedia_library.entities.works.sellable;

import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.works.sellable.bookings.Status;
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
    protected Status status;
    
    /**
     * 
     * @param adherent
     * @param date
     * @param status 
     */
    public Booking(Adherent adherent, Date date, Status status)
    {
        this.adherent = adherent;
        this.date = date;
        this.status = status;
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
    public Status getStatus()
    {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(Status status)
    {
        this.status = status;
    }
}
