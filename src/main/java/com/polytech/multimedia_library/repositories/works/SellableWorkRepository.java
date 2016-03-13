package com.polytech.multimedia_library.repositories.works;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.works.SellableWork;
import com.polytech.multimedia_library.entities.works.sellable.State;
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
public class SellableWorkRepository
{
    /**
     * Fetches a single sellable work from the database.
     * 
     * @param id The sellable work's id.
     * @return The sellable work if it exists, <code>null</code> otherwise.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public SellableWork fetch(int id)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_oeuvrevente`, `titre_oeuvrevente`, `etat_oeuvrevente`, `prix_oeuvrevente`, `id_proprietaire` " +
            "FROM `oeuvrevente` " +
            "WHERE `id_oeuvrevente` = ? " +
            "LIMIT 1"
        );

        // Then, bind parameters
        query.setInt(1, id);

        // Finally, execute it
        ResultSet resultSet = query.executeQuery();
        
        return resultSet.next() ? this.buildEntity(resultSet) : null;
    }
    
    /**
     * Fetches every sellable works from database.
     * 
     * @return The list of sellable works.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public List<SellableWork> fetchAll()
    throws NamingException, SQLException
    {
        // Initialize vars
        List<SellableWork> sellableWorks = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_oeuvrevente`, `titre_oeuvrevente`, `etat_oeuvrevente`, `prix_oeuvrevente`, `id_proprietaire` " +
            "FROM `oeuvrevente`"
        );

        // Then, execute it
        ResultSet resultSet = query.executeQuery();

        while(resultSet.next())
        {
            sellableWorks.add(this.buildEntity(resultSet));
        }
        
        return sellableWorks;
    }
    
    /**
     * Saves a sellable work into the database by, either inserting it
     * or updating it.
     * 
     * @param work The sellable work to save.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void save(SellableWork work)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != work.getId())
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "UPDATE `oeuvrevente` " +
                "SET " +
                    "`titre_oeuvrevente` = ?, " +
                    "`etat_oeuvrevente` = ?, " +
                    "`prix_oeuvrevente` = ?, " +
                    "`id_proprietaire` = ? " +
                "WHERE `id_oeuvrevente` = ? " +
                "LIMIT 1"
            );

            // Then, bind parameters
            query.setString(1, work.getName());
            query.setString(2, work.getState().toString());
            query.setFloat(3, work.getPrice());
            query.setInt(4, work.getOwner().getId());
            query.setInt(5, work.getId());

            // Finally, execute it
            query.executeUpdate();
        }
        else
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "INSERT INTO `oeuvrevente` " +
                "(`titre_oeuvrevente`, `etat_oeuvrevente`, `prix_oeuvrevente`, `id_proprietaire`) " +
                "VALUES " +
                "(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            // Then, bind parameters
            query.setString(1, work.getName());
            query.setString(2, work.getState().toString());
            query.setFloat(3, work.getPrice());
            query.setInt(4, work.getOwner().getId());

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
     * Deletes a single sellable work from the database.
     * 
     * @param work The sellable work to delete.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void delete(SellableWork work)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != work.getId())
        {
            // Build query
            PreparedStatement bookingsDeletion = connection.prepare(
                "DELETE FROM `oeuvrevente` " +
                "WHERE `id_oeuvrevente` = ? " +
                "LIMIT 1"
            );

            // Then, bind parameters
            bookingsDeletion.setInt(1, work.getId());

            // Finally, execute it
            bookingsDeletion.executeUpdate();
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
    protected SellableWork buildEntity(ResultSet resultSet)
    throws NamingException, SQLException
    {
        OwnerRepository ownerRepository = new OwnerRepository();
        
        return new SellableWork(
            resultSet.getInt("id_oeuvrevente"),
            resultSet.getString("titre_oeuvrevente"),
            ownerRepository.fetch(resultSet.getInt("id_proprietaire")),
            resultSet.getFloat("prix_oeuvrevente"),
            State.valueOf(resultSet.getString("etat_oeuvrevente"))
        );
    }
}
