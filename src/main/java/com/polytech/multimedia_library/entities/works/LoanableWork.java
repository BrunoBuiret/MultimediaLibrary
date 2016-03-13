package com.polytech.multimedia_library.entities.works;

import com.polytech.multimedia_library.entities.Owner;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class LoanableWork extends AbstractWork
{
    public LoanableWork()
    {
        this(0, "", null);
    }
    
    public LoanableWork(String name, Owner owner)
    {
        this(0, name, owner);
    }
    
    public LoanableWork(int id, String name, Owner owner)
    {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }
}
