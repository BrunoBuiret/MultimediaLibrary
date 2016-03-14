package com.polytech.multimedia_library.entities.works.sellable;

import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.works.SellableWork;
import com.polytech.multimedia_library.entities.works.sellable.bookings.Status;
import java.util.Date;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Booking
{
    /**
     * The booking's beneficiary.
     */
    protected Adherent adherent;
    
    /**
     * The booking's date.
     */
    protected Date date;
    
    /**
     * The booking' status.
     */
    protected Status status;
    
    /**
     * Creates a new booking to be used with {@link SellableWork}.
     * 
     * @param adherent The booking's beneficiary.
     * @param date The booking's date.
     * @param status The booking' status.
     */
    public Booking(Adherent adherent, Date date, Status status)
    {
        this.adherent = adherent;
        this.date = date;
        this.status = status;
    }

    /**
     * Gets a booking's beneficiary.
     * 
     * @return The booking's beneficiary.
     */
    public Adherent getAdherent()
    {
        return adherent;
    }

    /**
     * Sets a booking's beneficiary.
     * 
     * @param adherent The booking's beneficiary.
     */
    public void setAdherent(Adherent adherent)
    {
        this.adherent = adherent;
    }

    /**
     * Gets a booking's date.
     * 
     * @return The booking's date.
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Sets a booking's date.
     * 
     * @param date The booking's date.
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * Gets a booking' status.
     * 
     * @return The booking' status.
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     * Sets a booking' status.
     * 
     * @param status The booking' status.
     */
    public void setStatus(Status status)
    {
        this.status = status;
    }
}
