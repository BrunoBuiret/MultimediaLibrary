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
     * The work's booking.
     */
    Booking booking;
    
    /**
     * Creates a sellable work and fills it with part of its data. Usually used
     * when creating a new sellable work which hasn't been saved already.
     * 
     * @param name The work's name.
     * @param owner The work's owner.
     * @param price The work's price.
     * @param state The work's state.
     */
    public SellableWork(String name, Owner owner, double price, State state)
    {
        this(0, name, owner, price, state);
    }
    
    /**
     * Creates an abstract work and fills it with almost all its data. Usually
     * used when loading a sellable work from the database.
     * 
     * @param id The work's id.
     * @param name The work's name.
     * @param owner The work's owner.
     * @param price The work's price.
     * @param state The work's state.
     */
    public SellableWork(int id, String name, Owner owner, double price, State state)
    {
        this(id, name, owner, price, state, null);
    }
    
    /**
     * Creates an abstract work and fills it with all its data. Usually
     * used when loading a sellable work from the database.
     * 
     * @param id The work's id.
     * @param name The work's name.
     * @param owner The work's owner.
     * @param price The work's price.
     * @param state The work's state.
     * @param booking The work's booking.
     */
    public SellableWork(int id, String name, Owner owner, double price, State state, Booking booking)
    {
        super(id, name, owner);
        
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
     * Sets a work' state.
     * 
     * @param state The work' state.
     */
    public void setState(State state)
    {
        this.state = state;
    }
    
    /**
     * Tests if a work has a booking.
     * 
     * @return <code>true</code> if it has a booking, <code>false</code> otherwise.
     */
    public boolean hasBooking()
    {
        return null != this.booking;
    }
    
    /**
     * Gets a work's booking.
     * 
     * @return The work's booking.
     */
    public Booking getBooking()
    {
        return this.booking;
    }
    
    /**
     * Sets a work's booking.
     * 
     * @param booking The work's booking.
     */
    public void setBooking(Booking booking)
    {
        this.booking = booking;
    }
}
