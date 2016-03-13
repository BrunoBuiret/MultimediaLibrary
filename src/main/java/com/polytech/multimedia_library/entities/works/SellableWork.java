package com.polytech.multimedia_library.entities.works;

import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.entities.works.sellable.State;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class SellableWork extends AbstractWork
{
    /**
     * The work's price.
     */
    float price;
    
    /**
     * The work's state.
     */
    State state;
    
    public SellableWork()
    {
        this(0, "", null, 0, State.UNKNOWN);
    }
    
    public SellableWork(String name, Owner owner, float price, State state)
    {
        this(0, name, owner, price, state);
    }
    
    public SellableWork(int id, String name, Owner owner, float price, State state)
    {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.price = price;
        this.state = state;
    }

    /**
     * Gets a work's price.
     * 
     * @return The work's price.
     */
    public float getPrice()
    {
        return this.price;
    }

    /**
     * Sets a work's price.
     * 
     * @param price The work's price.
     */
    public void setPrice(float price)
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
}
