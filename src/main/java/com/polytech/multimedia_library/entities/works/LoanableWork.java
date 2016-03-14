package com.polytech.multimedia_library.entities.works;

import com.polytech.multimedia_library.entities.Owner;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class LoanableWork extends AbstractWork
{
    /**
     * {@inheritDoc}
     */
    public LoanableWork(String name, Owner owner)
    {
        super(name, owner);
    }
    
    /**
     * {@inheritDoc}
     */
    public LoanableWork(int id, String name, Owner owner)
    {
        super(id, name, owner);
    }
}
