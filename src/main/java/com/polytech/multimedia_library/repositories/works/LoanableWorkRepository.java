package com.polytech.multimedia_library.repositories.works;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.works.LoanableWork;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class LoanableWorkRepository
{
    /**
     * Fetches a single loanable work from the database.
     * 
     * @param id The loanable work's id.
     * @return The loanable work if it exists, <code>null</code> otherwise.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public LoanableWork fetch(int id)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_oeuvrepret`, `titre_oeuvrepret`, `id_proprietaire` " +
            "FROM `oeuvrepret` " +
            "WHERE `id_oeuvrepret` = ? " +
            "LIMIT 1"
        );

        // Then, bind parameters
        query.setInt(1, id);

        // Finally, execute it
        ResultSet resultSet = query.executeQuery();
        
        return resultSet.next() ? this.buildEntity(resultSet) : null;
    }
    
    /**
     * Fetches every loanable works from database.
     * 
     * @return The list of loanable works.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public List<LoanableWork> fetchAll()
    throws NamingException, SQLException
    {
        // Initialize vars
        List<LoanableWork> loanableWorks = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_oeuvrepret`, `titre_oeuvrepret`, `id_proprietaire` " +
            "FROM `oeuvrepret`"
        );

        // Then, execute it
        ResultSet resultSet = query.executeQuery();

        while(resultSet.next())
        {
            loanableWorks.add(this.buildEntity(resultSet));
        }
        
        return loanableWorks;
    }
    
    /**
     * Saves a loanable work into the database by, either inserting it
     * or updating it.
     * 
     * @param work The loanable work to save.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void save(LoanableWork work)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != work.getId())
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "UPDATE `oeuvrepret` " +
                "SET " +
                    "`titre_oeuvrepret` = ?, " +
                    "`id_proprietaire` = ? " +
                "WHERE `id_oeuvrepret` = ? " +
                "LIMIT 1"
            );

            // Then, bind parameters
            query.setString(1, work.getName());
            query.setInt(2, work.getOwner().getId());
            query.setInt(3, work.getId());

            // Finally, execute it
            query.executeUpdate();
        }
        else
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "INSERT INTO `oeuvrepret` " +
                "(`titre_oeuvrepret`, `id_proprietaire`) " +
                "VALUES " +
                "(?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            // Then, bind parameters
            query.setString(1, work.getName());
            query.setInt(2, work.getOwner().getId());

            // Finally, execute it
            query.executeUpdate();

            // Fetch the newly generated id
            ResultSet resultSet = query.getGeneratedKeys();

            if(resultSet.next())
            {
                work.setId(resultSet.getInt(1));
            }
        }
    }
    
    /**
     * Deletes a single loanable work from the database.
     * 
     * @param work The sellable work to delete.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void delete(LoanableWork work)
    throws NamingException, SQLException
    {
    // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != work.getId())
        {
            try
            {
                // Start a transaction
                connection.beginTransaction();

                // First, delete the loans
                PreparedStatement loansDeletion = connection.prepare(
                    "DELETE FROM `emprunt` " +
                    "WHERE `id_oeuvrepret` = ? "
                );
                loansDeletion.setInt(1, work.getId());
                loansDeletion.executeUpdate();
                
                // Then, delete the works
                PreparedStatement worksDeletion = connection.prepare(
                    "DELETE FROM `oeuvrepret` " +
                    "WHERE `id_oeuvrepret` = ? " +
                    "LIMIT 1"
                );
                worksDeletion.setInt(1, work.getId());
                worksDeletion.executeUpdate();
                
                // Commit the transaction
                connection.endTransaction();
            }
            catch(SQLException mainException)
            {
                try
                {
                    connection.cancelTransaction();
                }
                catch(SQLException secondaryException)
                {
                    // Register the previous exception
                    secondaryException.addSuppressed(mainException);
                    
                    // Then, re-throw this one
                    throw secondaryException;
                }
            }
        }
        else
        {
            throw new IllegalArgumentException(
                "This work cannot be deleted because it doesn't have an id, " +
                "it might not be registered yet."
            );
        }
    }
    
    /**
     * Builds a sellable work from their data.
     * 
     * @param resultSet Data extracted from the database.
     * @return The newly built sellable work.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    protected LoanableWork buildEntity(ResultSet resultSet)
    throws SQLException, NamingException
    {
        OwnerRepository ownerRepository = new OwnerRepository();
        
        return new LoanableWork(
            resultSet.getInt("id_oeuvrepret"),
            resultSet.getString("titre_oeuvrepret"),
            ownerRepository.fetch(resultSet.getInt("id_proprietaire"))
        );
    }
}
