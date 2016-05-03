package com.polytech.multimedia_library.repositories.works;

import com.polytech.multimedia_library.entities.Oeuvrepret;
import com.polytech.multimedia_library.repositories.AbstractRepository;
import com.polytech.multimedia_library.repositories.RepositoryException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class LoanableWorksRepository extends AbstractRepository
{
    /**
     * Fetches a single existing loanable work from the database.
     *
     * @param id The work's id.
     * @return The work, or <code>null</code> if there are no matching work.
     */
    public Oeuvrepret fetch(int id)
    {
        // Fetch the work
        try
        {
            return this.entityManager.find(Oeuvrepret.class, id);
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                String.format(
                    "Impossible de récupérer l'oeuvre à prêter d'identifiant #%d.",
                    id
                )
            );
        }
    }

    /**
     * Fetches a list of loanable works from the database according to their id.
     *
     * @param ids The list of work's id.
     * @return The list of works.
     */
    public List<Oeuvrepret> fetch(List<Integer> ids)
    {
        // Initialize vars
        List<Oeuvrepret> works;

        // Fetch the works
        if(ids.size() > 0)
        {
            try
            {
                works = (List<Oeuvrepret> ) this.entityManager.createQuery(
                    "SELECT o " +
                    "FROM Oeuvrepret o " +
                    "WHERE o.idOeuvrepret IN(:ids) " +
                    "ORDER BY o.titreOeuvrepret"
                )
                .setParameter("ids", ids)
                .getResultList();
            }
            catch(Exception ex)
            {
                throw new RepositoryException(
                    ex,
                    "Impossible de récupérer la liste des oeuvres à prêter."
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
     * Fetches every existing loanable work from the database.
     *
     * @return The list of works.
     */
    public List<Oeuvrepret> fetchAll()
    {
        // Initialize vars
        List<Oeuvrepret> works;

        // Fetch the works
        try
        {
            works = (List<Oeuvrepret>) this.entityManager.createQuery(
                "SELECT o FROM Oeuvrepret o ORDER BY o.titreOeuvrepret"
            ).getResultList();
        }
        catch(Exception ex)
        {
            throw new RepositoryException(
                ex,
                "Impossible de récupérer la liste des oeuvres à prêter."
            );
        }

        return works;
    }

    /**
     * Saves a loanable work into the database.
     *
     * @param work The work to save.
     */
    public void save(Oeuvrepret work)
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

            System.out.println(ex);

            for(StackTraceElement e : ex.getStackTrace())
            {
                System.out.println(e);
            }

            throw new RepositoryException(
                ex,
                "Impossible de sauvegarder l'oeuvre à prêter."
            );
        }
    }

    /**
     * Removes a loanable work from the database.
     *
     * @param work The work to remove.
     */
    public void delete(Oeuvrepret work)
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
                "Impossible de supprimer l'oeuvre à prêter."
            );
        }
    }

    /**
     * Removes a list of loanable works from the database.
     *
     * @param works The list of works to remove.
     */
    public void delete(List<Oeuvrepret> works)
    {
        // Initialize vars
        EntityTransaction transaction = this.entityManager.getTransaction();

        try
        {
            transaction.begin();

            works.stream().forEach((work) ->
            {
                this.entityManager.remove(work);
            });

            this.entityManager.flush();
            transaction.commit();
        }
        catch(Exception ex)
        {
            transaction.rollback();

            throw new RepositoryException(
                ex,
                "Impossible de supprimer les oeuvres à prêter."
            );
        }
    }
}
