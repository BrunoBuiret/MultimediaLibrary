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
     * Gets a state's code.
     * 
     * @return The state's code.
     */
    public String getCode()
    {
        return this.code;
    }
    
    /**
     * Gets a state from its code.
     * 
     * @param code The state's code.
     * @return The state, or <code>null</code> if no matching state has been found.
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
