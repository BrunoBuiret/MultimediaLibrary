package com.polytech.multimedia_library.entities.works.sellable.bookings;

/**
 *
 * @author bruno
 */
public enum Status
{
    PENDING("en attente"),
    APPROVED("confirmee"),
    REJECTED("rejetee");
    
    /**
     * 
     */
    protected String code;
    
    /**
     * 
     * @param code 
     */
    Status(String code)
    {
        this.code = code;
    }

    /**
     * 
     * @return 
     */
    public String getCode()
    {
        return code;
    }

    /**
     * 
     * @param code 
     */
    public void setCode(String code)
    {
        this.code = code;
    }
    
    /**
     * 
     * @param code
     * @return 
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
