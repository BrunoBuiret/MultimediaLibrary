package com.polytech.multimedia_library.session;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class Flash
{
    /**
     * 
     */
    protected final String type;
    
    /**
     * 
     */
    protected final String contents;
    
    /**
     * 
     * @param type
     * @param contents 
     */
    public Flash(String type, String contents)
    {
        this.type = type;
        this.contents = contents;
    }

    /**
     * 
     * @return 
     */
    public String getType()
    {
        return type;
    }

    /**
     * 
     * @return 
     */
    public String getContents()
    {
        return contents;
    }
}
