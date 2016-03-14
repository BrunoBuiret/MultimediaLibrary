package com.polytech.multimedia_library.entities.works;

import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.entities.works.sellable.Booking;
import com.polytech.multimedia_library.entities.works.sellable.State;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class SellableWork extends AbstractWork
{
    /**
     * The work's price.
     */
    double price;
    
    /**
     * The work's state.
     */
    State state;
    
    /**
     * 
     */
    Booking booking;
    
    /**
     * 
     */
    public SellableWork()
    {
        this(0, "", null, 0, State.UNKNOWN);
    }
    
    /**
     * 
     * @param name
     * @param owner
     * @param price
     * @param state 
     */
    public SellableWork(String name, Owner owner, double price, State state)
    {
        this(0, name, owner, price, state);
    }
    
    /**
     * 
     * @param id
     * @param name
     * @param owner
     * @param price
     * @param state 
     */
    public SellableWork(int id, String name, Owner owner, double price, State state)
    {
        this(id, name, owner, price, state, null);
    }
    
    /**
     * 
     * @param id
     * @param name
     * @param owner
     * @param price
     * @param state
     * @param booking 
     */
    public SellableWork(int id, String name, Owner owner, double price, State state, Booking booking)
    {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.price = price;
        this.state = state;
        this.booking = booking;
    }

    /**
     * Gets a work's price.
     * 
     * @return The work's price.
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * Sets a work's price.
     * 
     * @param price The work's price.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Gets a work' state.
     * 
     * @return The work' state.
     */
    public State getState()
    {
        return this.state;
    }

    /**
     * Sets a woek' state.
     * 
     * @param state The work' state.
     */
    public void setState(State state)
    {
        this.state = state;
    }
    
    /**
     * 
     * @return 
     */
    public boolean hasBooking()
    {
        return null != this.booking;
    }
    
    /**
     * 
     * @return 
     */
    public Booking getBooking()
    {
        return this.booking;
    }
    
    /**
     * 
     * @param booking 
     */
    public void setBooking(Booking booking)
    {
        this.booking = booking;
    }
}
