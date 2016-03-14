package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.works.LoanableWork;
import java.util.Date;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Loan
{
    int id;
    
    LoanableWork work;
    
    Adherent adherent;
    
    Date dateStart;
    
    Date dateEnd;
    
    public Loan(LoanableWork work, Adherent adherent, Date dateStart, Date dateEnd)
    {
        this(0, work, adherent, dateStart, dateEnd);
    }
    
    public Loan(int id, LoanableWork work, Adherent adherent, Date dateStart, Date dateEnd)
    {
        this.id = id;
        this.work = work;
        this.adherent = adherent;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public LoanableWork getWork()
    {
        return work;
    }

    public void setWork(LoanableWork work)
    {
        this.work = work;
    }

    public Adherent getAdherent()
    {
        return adherent;
    }

    public void setAdherent(Adherent adherent)
    {
        this.adherent = adherent;
    }

    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart(Date dateStart)
    {
        this.dateStart = dateStart;
    }

    public Date getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd)
    {
        this.dateEnd = dateEnd;
    }
}
