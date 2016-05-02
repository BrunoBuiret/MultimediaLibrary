package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.entities.Proprietaire;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class OwnersRepository extends AbstractRepository
{
    /**
     * Fetches a single existing owner from the database.
     * 
     * @param id The owner's id.
     * @return The owner, or <code>null</code> if there are no matching owner.
     */
    public Proprietaire fetch(int id)
    {
        // Fetch the owner
        try
        {
            return this.entityManager.find(Proprietaire.class, id);
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                String.format(
                    "Impossible de récupérer le propriétaire d'identifiant #%d.",
                    id
                )
            );
        }
    }
    
    /**
     * Fetches a list of owners from the database according to their id.
     * 
     * @param ids The list of owner's id.
     * @return The list of owners.
     */
    public List<Proprietaire> fetch(List<Integer> ids)
    {
        // Initialize vars
        List<Proprietaire> owners;
        
        // Fetch the owners
        if(ids.size() > 0)
        {
            try
            {
                owners = (List<Proprietaire>) this.entityManager.createQuery(
                    "SELECT p " +
                    "FROM Proprietaire p " +
                    "WHERE p.idProprietaire IN(:ids) " +
                    "ORDER BY p.nomProprietaire, p.prenomProprietaire"
                )
                .setParameter("ids", ids)
                .getResultList();
            }
            catch(Exception ex)
            {
                throw new RepositoryException(
                    ex,
                    "Impossible de récupérer la liste des propriétaires."
                );
            }
        }
        else
        {
            owners = new ArrayList<>();
        }
        
        return owners;
    }
    
    /**
     * Fetches every existing owner from the database.
     * 
     * @return The list of owners.
     */
    public List<Proprietaire> fetchAll()
    {
        // Initialize vars
        List<Proprietaire> owners;
        
        // Fetch the adherents
        try
        {
            owners = (List<Proprietaire>) this.entityManager.createQuery(
                "SELECT p FROM Proprietaire p ORDER BY p.nomProprietaire, p.prenomProprietaire"
            ).getResultList();
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                "Impossible de récupérer la liste des propriétaires."
            );
        }
        
        return owners;
    }
    
    /**
     * Saves an owner into the database.
     * 
     * @param owner The owner to save.
     */
    public void save(Proprietaire owner)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        // Save the owner
        try
        {
            transaction.begin();
            this.entityManager.merge(owner);
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
            
            throw new RepositoryException(
                ex,
                "Impossible de sauvegarder le propriétaire."
            );
        }
    }
    
    /**
     * Removes an owner from the database.
     * 
     * @param owner The owner to remove.
     */
    public void delete(Proprietaire owner)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        // Delete the owner
        try
        {
            transaction.begin();
            this.entityManager.remove(owner);
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
            
            throw new RepositoryException(
                ex,
                "Impossible de supprimer le propriétaire."
            );
        }
    }
    
    /**
     * Removes a list of owners from the database.
     * 
     * @param owners The list of owners to remove.
     */
    public void delete(List<Proprietaire> owners)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        try
        {
            transaction.begin();
            
            for(Proprietaire owner : owners)
            {
                this.entityManager.remove(owner);
            }
            
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();
            
            throw new RepositoryException(
                ex,
                "Impossible de supprimer les propriétaires."
            );
        }
    }
}
