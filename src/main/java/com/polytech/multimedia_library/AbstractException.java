package com.polytech.multimedia_library;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public abstract class AbstractException extends RuntimeException
{
    /**
     * The title to be displayed on the page.
     */
    protected String title;
    
    /**
     * Creates a new abstract exception.
     * 
     * @param ex The actual exception that occured.
     * @param message The message to be displayed.
     */
    public AbstractException(Exception ex, String message)
    {
        this(ex, null, message);
    }
    
    /**
     * Creates a new abstract exception.
     * 
     * @param ex The actual exception that occured.
     * @param title The title to be displayed.
     * @param message The message to be displayed.
     */
    public AbstractException(Exception ex, String title, String message)
    {
        // Call super constructor
        super(message, ex);
        
        // Initialize properties
        this.title = title;
    }
    
    /**
     * Gets the title to be displayed.
     * 
     * @return The title.
     */
    public String getTitle()
    {
        return this.title;
    }
}
