package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.works.LoanableWork;
import java.util.Date;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Loan
{
    /**
     * The loan's id.
     */
    int id;
    
    /**
     * The loan's associated work.
     */
    LoanableWork work;
    
    /**
     * The loan's beneficiary.
     */
    Adherent adherent;
    
    /**
     * The loan's date of start.
     */
    Date dateStart;
    
    /**
     * The loan's date of end.
     */
    Date dateEnd;
    
    /**
     * Creates a loan and fills it with part of its data. Usually used
     * when creating a new loan which hasn't been saved already.
     * 
     * @param work The loan's associated work.
     * @param adherent The loan's beneficiary.
     * @param dateStart The loan's date of start.
     * @param dateEnd The loan's date of end.
     */
    public Loan(LoanableWork work, Adherent adherent, Date dateStart, Date dateEnd)
    {
        this(0, work, adherent, dateStart, dateEnd);
    }
    
    /**
     * Creates a loan and fills it with data. Usually used when loading
     * a loan from the database.
     * 
     * @param id The loan's id.
     * @param work The loan's associated work.
     * @param adherent The loan's beneficiary.
     * @param dateStart The loan's date of start.
     * @param dateEnd The loan's date of end.
     */
    public Loan(int id, LoanableWork work, Adherent adherent, Date dateStart, Date dateEnd)
    {
        this.id = id;
        this.work = work;
        this.adherent = adherent;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Gets a loan's id.
     * 
     * @return The loan's id.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets a loan's id.
     * 
     * @param id The loan's id.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets a loan's associated work.
     * 
     * @return The associated work.
     */
    public LoanableWork getWork()
    {
        return this.work;
    }

    /**
     * Sets a loan's associated work.
     * 
     * @param work The associated work.
     */
    public void setWork(LoanableWork work)
    {
        this.work = work;
    }

    /**
     * Gets a loan's beneficiary.
     * 
     * @return The loan's beneficiary.
     */
    public Adherent getAdherent()
    {
        return this.adherent;
    }

    /**
     * Sets a loan's beneficiary.
     * 
     * @param adherent The loan's beneficiary.
     */
    public void setAdherent(Adherent adherent)
    {
        this.adherent = adherent;
    }

    /**
     * Gets a loan's date of start.
     * 
     * @return The date of start.
     */
    public Date getDateStart()
    {
        return this.dateStart;
    }

    /**
     * Sets a loan's date of start.
     * 
     * @param dateStart The date of start.
     */
    public void setDateStart(Date dateStart)
    {
        this.dateStart = dateStart;
    }

    /**
     * Gets a loan's date of end.
     * 
     * @return The date of end.
     */
    public Date getDateEnd()
    {
        return this.dateEnd;
    }

    /**
     * Sets a loan's date of end.
     * 
     * @param dateEnd The date of end.
     */
    public void setDateEnd(Date dateEnd)
    {
        this.dateEnd = dateEnd;
    }
}
