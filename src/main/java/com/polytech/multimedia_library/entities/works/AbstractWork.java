package com.polytech.multimedia_library.entities.works;

import com.polytech.multimedia_library.entities.Owner;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
abstract public class AbstractWork
{
    /**
     * The work's id.
     */
    int id;
    
    /**
     * The work's name.
     */
    String name;
    
    /**
     * The work's owner.
     */
    Owner owner;
    
    /**
     * Creates an abstract work and fills it with part of its data. Usually used
     * when creating a new abstract work which hasn't been saved already.
     * 
     * @param name The work's name.
     * @param owner The work's owner.
     */
    public AbstractWork(String name, Owner owner)
    {
        this(0, name, owner);
    }
    
    /**
     * Creates an abstract work and fills it with data. Usually used when loading
     * an abstract work from the database.
     * 
     * @param id The work's id.
     * @param name The work's name.
     * @param owner The work's owner.
     */
    public AbstractWork(int id, String name, Owner owner)
    {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    /**
     * Gets a work's id.
     * 
     * @return The work's id.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets a work's id.
     * 
     * @param id The work's id.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets a work's name.
     * 
     * @return The work's name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets a work's name.
     * 
     * @param name The work's name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets a work's owner.
     * 
     * @return The work's owner.
     */
    public Owner getOwner()
    {
        return this.owner;
    }

    /**
     * Sets a work's owner.
     * 
     * @param owner The work's owner, or <code>null</code> if they haven't
     * been fetched.
     */
    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }
}
