package com.polytech.multimedia_library.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public abstract class AbstractRepository
{
    /**
     * 
     */
    protected EntityManager entityManager;
    
    /**
     * Creates a new abstract repository.
     */
    public AbstractRepository()
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("POeuvre");
        this.entityManager = factory.createEntityManager();
    }
}
