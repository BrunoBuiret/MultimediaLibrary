package com.polytech.multimedia_library.entities.works.sellable;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public enum State
{
    L("L"),
    R("R"),
    UNKNOWN("?");
    
    /**
     * The state's code.
     */
    protected String code;
    
    /**
     * Creates a new state.
     * 
     * @param code The state's code.
     */
    State(String code)
    {
        this.code = code;
    }
    
    /**
     * 
     * @return 
     */
    public String getCode()
    {
        return this.code;
    }
    
    /**
     * 
     * @param code
     * @return 
     */
    public static State fromCode(String code)
    {
        if(null != code)
        {
            for(State state : State.values())
            {
                if(code.equalsIgnoreCase(state.getCode()))
                {
                    return state;
                }
            }
        }
        
        return null;
    }
}
