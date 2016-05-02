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
    protected static EntityManagerFactory FACTORY;
    
    /**
     * The entity manager used to persist entities into the database.
     */
    protected EntityManager entityManager;
    
    /**
     * 
     */
    static
    {
        AbstractRepository.FACTORY = Persistence.createEntityManagerFactory("POeuvre");
    }
    
    /**
     * Creates a new abstract repository.
     */
    public AbstractRepository()
    {
        this.entityManager = AbstractRepository.FACTORY.createEntityManager();
    }
}
