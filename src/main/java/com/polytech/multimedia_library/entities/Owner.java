package com.polytech.multimedia_library.entities;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Owner
{
    int id;

    String firstName;

    String lastName;
    
    public Owner()
    {
        this(0, "", "");
    }
    
    public Owner(String firstName, String lastName)
    {
        this(0, firstName, lastName);
    }
    
    public Owner(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
