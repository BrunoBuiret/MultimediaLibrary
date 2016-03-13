package com.polytech.multimedia_library.entities;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Adherent
{
    /**
     * The adherent's id.
     */
    protected int id;
    
    /**
     * The adherent's first name.
     */
    protected String firstName;
    
    /**
     * The adherent's last name.
     */
    protected String lastName;
    
    /**
     * The adherent's town.
     */
    protected String town;
    
    /**
     * Creates an adherent without any data.
     */
    public Adherent()
    {
        this(0, "", "", "");
    }
    
    /**
     * Creates an adherent and fills them with part of their data. Usually
     * used when creating a new adherent who hasn't been saved already.
     * 
     * @param firstName The adherent's first name.
     * @param lastName The adherent's last name.
     * @param town  The adherent's town.
     */
    public Adherent(String firstName, String lastName, String town)
    {
        this(0, firstName, lastName, town);
    }
    
    /**
     * Creates an adherent and fills them with data.
     * 
     * @param id The adherent's id.
     * @param firstName The adherent's first name.
     * @param lastName The adherent's last name.
     * @param town  The adherent's town.
     */
    public Adherent(int id, String firstName, String lastName, String town)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.town = town;
    }

    /**
     * Gets an adherent's id.
     * 
     * @return int The adherent's id.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets an adherent's id.
     * 
     * @param id The adherent's id.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets an adherent's first name.
     * 
     * @return The adherent's first name.
     */
    public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * Sets an adherent's first name.
     * 
     * @param firstName The adherent's first name.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets an adherent's last name.
     * 
     * @return The adherent's last name.
     */
    public String getLastName()
    {
        return this.lastName;
    }

    /**
     * Sets an adherent's last name.
     * 
     * @param lastName The adherent's last name.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets an adherent's town.
     * 
     * @return The adherent's town.
     */
    public String getTown()
    {
        return this.town;
    }

    /**
     * Sets an adherent's town.
     * 
     * @param town The adherent's town.
     */
    public void setTown(String town)
    {
        this.town = town;
    }
}
