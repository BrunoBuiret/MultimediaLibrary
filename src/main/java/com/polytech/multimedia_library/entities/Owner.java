package com.polytech.multimedia_library.entities;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Owner
{
    /**
     * The owner's id.
     */
    int id;

    /**
     * The owner's first name.
     */
    String firstName;

    /**
     * The owner's last name.
     */
    String lastName;
    
    /**
     * Creates an owner and fills them with part of their data. Usually used
     * when creating a new owner who hasn't been saved already.
     * 
     * @param firstName The owner's first name.
     * @param lastName The owner's last name.
     */
    public Owner(String firstName, String lastName)
    {
        this(0, firstName, lastName);
    }
    
    /**
     * Creates an owner and fills them with data. Usually used when loading
     * an owner from the database.
     * 
     * @param id The owner's id.
     * @param firstName The owner's first name.
     * @param lastName The owner's last name.
     */
    public Owner(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets an owner's id.
     * 
     * @return The owner's id.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets an owner's id.
     * 
     * @param id The owner's id.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * Gets an owner's first name.
     * 
     * @return The owner's first name.
     */
    public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * Sets an owner's first name.
     * 
     * @param firstName The owner's first name.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets an owner's last name.
     * 
     * @return The owner's last name.
     */
    public String getLastName()
    {
        return this.lastName;
    }

    /**
     * Sets an owner's last name.
     * 
     * @param lastName The owner's last name.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
