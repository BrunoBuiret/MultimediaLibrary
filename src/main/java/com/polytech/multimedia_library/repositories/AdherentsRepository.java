package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.entities.Adherent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class AdherentsRepository extends AbstractRepository
{
    /**
     * Fetches a single existing adherent from the database.
     *
     * @param id The adherent's id.
     * @return The adherent, or <code>null</code> if there are no matching adherent.
     */
    public Adherent fetch(int id)
    {
        // Fetch the adherent
        try
        {
            return this.entityManager.find(Adherent.class, id);
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                String.format(
                    "Impossible de récupérer l'adhérent d'identifiant #%d.",
                    id
                )
            );
        }
    }

    /**
     * Fetches a list of adherents from the database according to their id.
     *
     * @param ids The list of adherent's id.
     * @return The list of adherents.
     */
    public List<Adherent> fetch(List<Integer> ids)
    {
        // Initialize vars
        List<Adherent> adherents;

        // Fetch the adherents
        if(ids.size() > 0)
        {
            try
            {
                adherents = (List<Adherent>) this.entityManager.createQuery(
                    "SELECT a " +
                    "FROM Adherent a " +
                    "WHERE a.idAdherent IN(:ids) " +
                    "ORDER BY a.nomAdherent, a.prenomAdherent"
                )
                .setParameter("ids", ids)
                .getResultList();
            }
            catch(Exception ex)
            {
                throw new RepositoryException(
                    ex,
                    "Impossible de récupérer la liste des adhérents."
                );
            }
        }
        else
        {
            adherents = new ArrayList<>();
        }

        return adherents;
    }

    /**
     * Fetches every existing adherent from the database.
     *
     * @return The list of adherents.
     */
    public List<Adherent> fetchAll()
    {
        // Initialize vars
        List<Adherent> adherents;

        // Fetch the adherents
        try
        {
            adherents = (List<Adherent>) this.entityManager.createQuery(
                "SELECT a FROM Adherent a ORDER BY a.nomAdherent, a.prenomAdherent"
            ).getResultList();
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                "Impossible de récupérer la liste des adhérents."
            );
        }

        return adherents;
    }

    /**
     * Saves an adherent into the database.
     *
     * @param adherent The adherent to save.
     */
    public void save(Adherent adherent)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();

        // Save the adherent
        try
        {
            transaction.begin();
            this.entityManager.merge(adherent);
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();

            throw new RepositoryException(
                ex,
                "Impossible de sauvegarder l'adhérent."
            );
        }
    }

    /**
     * Removes an adherent from the database.
     *
     * @param adherent The adherent to remove.
     */
    public void delete(Adherent adherent)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();

        // Delete the adherent
        try
        {
            transaction.begin();
            this.entityManager.remove(adherent);
            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();

            throw new RepositoryException(
                ex,
                "Impossible de supprimer l'adhérent."
            );
        }
    }

    /**
     * Removes a list of adherents from the database.
     *
     * @param adherents The list of adherents to remove.
     */
    public void delete(List<Adherent> adherents)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();

        try
        {
            transaction.begin();

            adherents.stream().forEach((adherent) ->
            {
                this.entityManager.remove(adherent);
            });

            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();

            throw new RepositoryException(
                ex,
                "Impossible de supprimer les adhérents."
            );
        }
    }
}
