package com.polytech.multimedia_library.flashes;

import java.io.Serializable;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class Flash implements Serializable
{
    /**
     * The flash message's type.
     */
    protected String type;
    
    /**
     * The flash message's contents.
     */
    protected String contents;
    
    /**
     * Creates a new flash message.
     * 
     * @param type The flash message's type.
     * @param contents The flash message's contents.
     */
    public Flash(String type, String contents)
    {
        this.type = type;
        this.contents = contents;
    }

    /**
     * Gets a flash message's type.
     * 
     * @return The flash message's type.
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets a flash message's type.
     * 
     * @param type The flash message's type.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Gets a flash message's contents.
     * 
     * @return The flash message's contents.
     */
    public String getContents()
    {
        return contents;
    }

    /**
     * Sets a flash message's contents.
     * 
     * @param contents The flash message's contents.
     */
    public void setContents(String contents)
    {
        this.contents = contents;
    }
}
