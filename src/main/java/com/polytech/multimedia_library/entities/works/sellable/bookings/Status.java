package com.polytech.multimedia_library.entities.works.sellable.bookings;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public enum Status
{
    PENDING("en attente"),
    APPROVED("confirmee"),
    REJECTED("rejetee");
    
    /**
     * The status' code.
     */
    protected String code;
    
    /**
     * Creates a new status.
     * 
     * @param code The status' code.
     */
    Status(String code)
    {
        this.code = code;
    }

    /**
     * Gets a status' code.
     * 
     * @return The status' code.
     */
    public String getCode()
    {
        return code;
    }
    
    /**
     * Gets a status from its code.
     * 
     * @param code The status' code.
     * @return The status, or <code>null</code> if no matching status has been found.
     */
    public static Status fromCode(String code)
    {
        if(null != code)
        {
            for(Status status : Status.values())
            {
                if(code.equalsIgnoreCase(status.getCode()))
                {
                    return status;
                }
            }
        }
        
        return null;
    }
}
