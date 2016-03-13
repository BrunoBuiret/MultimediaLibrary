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
     */
    public LoanableWork fetch(int id)
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        try
        {
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

            if(resultSet.next())
            {
                return this.buildEntity(resultSet);
            }
        }
        catch(SQLException e)
        {
            // @todo Error handling.
        }
        
        return null;
    }
    
    /**
     * Fetches every loanable works from database.
     * 
     * @return The list of loanable works.
     */
    public List<LoanableWork> fetchAll()
    {
        List<LoanableWork> loanableWorks = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        try
        {
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
        }
        catch(SQLException e)
        {
            // @todo Error handling.
        }
        
        return loanableWorks;
    }
    
    /**
     * Saves a loanable work into the database by, either inserting it
     * or updating it.
     * 
     * @param work The loanable work to save.
     */
    public void save(LoanableWork work)
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        try
        {
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
        catch(SQLException e)
        {
            // @todo Error handling
            e.printStackTrace();
        }
    }
    
    /**
     * Deletes a single loanable work from the database.
     * 
     * @param work The sellable work to delete.
     */
    public void delete(LoanableWork work)
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != work.getId())
        {
            try
            {
                // Build query
                PreparedStatement bookingsDeletion = connection.prepare(
                    "DELETE FROM `oeuvrepret` " +
                    "WHERE `id_oeuvrepret` = ? " +
                    "LIMIT 1"
                );
                
                // Then, bind parameters
                bookingsDeletion.setInt(1, work.getId());
                
                // Finally, execute it
                bookingsDeletion.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // @todo Error handling
        }
    }
    
    /**
     * Builds a sellable work from their data.
     * 
     * @param resultSet Data extracted from the database.
     * @return The newly built sellable work.
     * @throws SQLException
     */
    protected LoanableWork buildEntity(ResultSet resultSet)
    throws SQLException
    {
        OwnerRepository ownerRepository = new OwnerRepository();
        
        return new LoanableWork(
            resultSet.getInt("id_oeuvrepret"),
            resultSet.getString("titre_oeuvrepret"),
            ownerRepository.fetch(resultSet.getInt("id_proprietaire"))
        );
    }
}
