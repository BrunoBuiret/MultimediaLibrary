package com.polytech.multimedia_library.repositories.works;

import com.polytech.multimedia_library.entities.Oeuvrevente;
import com.polytech.multimedia_library.repositories.AbstractRepository;
import com.polytech.multimedia_library.repositories.RepositoryException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class SellableWorksRepository extends AbstractRepository
{
    /**
     * Fetches a single existing sellable work from the database.
     * 
     * @param id The work's id.
     * @return The work, or <code>null</code> if there are no matching work.
     */
    public Oeuvrevente fetch(int id)
    {
        // Fetch the work
        try
        {
            return this.entityManager.find(Oeuvrevente.class, id);
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                String.format(
                    "Impossible de récupérer l'oeuvre à vendre d'identifiant #%d.",
                    id
                )
            );
        }
    }
    
    /**
     * Fetches a list of sellable works from the database according to their id.
     * 
     * @param ids The list of work's id.
     * @return The list of works.
     */
    public List<Oeuvrevente> fetch(List<Integer> ids)
    {
        // Initialize vars
        List<Oeuvrevente> works;
        
        // Fetch the works
        if(ids.size() > 0)
        {
            try
            {
                works = (List<Oeuvrevente> ) this.entityManager.createQuery(
                    "SELECT o " +
                    "FROM Oeuvrevente o " +
                    "WHERE o.idOeuvrevente IN(:ids) " +
                    "ORDER BY o.titreOeuvrevente"
                )
                .setParameter("ids", ids)
                .getResultList();
            }
            catch(Exception ex)
            {
                throw new RepositoryException(
                    ex,
                    "Impossible de récupérer la liste des oeuvres à vendre."
                );
            }
        }
        else
        {
            works = new ArrayList<>();
        }
        
        return works;
    }
    
    /**
     * Fetches every existing sellable work from the database.
     * 
     * @return The list of works.
     */
    public List<Oeuvrevente> fetchAll()
    {
        // Initialize vars
        List<Oeuvrevente> works;
        
        // Fetch the works
        try
        {
            works = (List<Oeuvrevente>) this.entityManager.createQuery(
                "SELECT o FROM Oeuvrevente o ORDER BY o.titreOeuvrevente"
            ).getResultList();
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                "Impossible de récupérer la liste des oeuvres à vendre."
            );
        }
        
        return works;
    }
    
    /**
     * Saves a sellable work into the database.
     * 
     * @param work The work to save.
     */
    public void save(Oeuvrevente work)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        // Save the work
        try
        {
            transaction.begin();
            this.entityManager.merge(work);
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
            
            throw new RepositoryException(
                ex,
                "Impossible de sauvegarder l'oeuvre à vendre."
            );
        }
    }
    
    /**
     * Removes a sellable work from the database.
     * 
     * @param work The work to remove.
     */
    public void delete(Oeuvrevente work)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        // Delete the work
        try
        {
            transaction.begin();
            this.entityManager.remove(work);
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
            
            throw new RepositoryException(
                ex,
                "Impossible de supprimer l'oeuvre à vendre."
            );
        }
    }
    
    /**
     * Removes a list of sellable works from the database.
     * 
     * @param works The list of works to remove.
     */
    public void delete(List<Oeuvrevente> works)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        try
        {
            transaction.begin();
            
            for(Oeuvrevente work : works)
            {
                this.entityManager.remove(work);
            }
            
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
            
            throw new RepositoryException(
                ex,
                "Impossible de supprimer les oeuvres à vendre."
            );
        }
    }
}
