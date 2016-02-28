package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.Owner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public class OwnerRepository extends AbstractRepository
{
    /**
     * Fetches a single owner from the database.
     * 
     * @param id The owner's id.
     * @return The owner if they exist, <code>null</code> otherwise.
     */
    public Owner fetch(int id)
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        try
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "SELECT `id_proprietaire`, `prenom_proprietaire`, `nom_proprietaire` " +
                "FROM `proprietaire` " +
                "WHERE `id_proprietaire` = ? " +
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
     * Fetches every owner from database.
     * 
     * @return The list of owner.
     */
    public List<Owner> fetchAll()
    {
        List<Owner> owners = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        try
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "SELECT `id_proprietaire`, `prenom_proprietaire`, `nom_proprietaire` " +
                "FROM `proprietaire` "
            );

            // Then, execute it
            ResultSet resultSet = query.executeQuery();

            while(resultSet.next())
            {
                owners.add(this.buildEntity(resultSet));
            }
        }
        catch(SQLException e)
        {
            // @todo Error handling.
        }
        
        return owners;
    }
    
    /**
     * Saves an owner into the database by, either inserting them
     * or updating them.
     * 
     * @param owner The owner to save.
     */
    public void save(Owner owner)
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        try
        {
            if(0 != owner.getId())
            {
                // Build query
                PreparedStatement query = connection.prepare(
                    "UPDATE `proprietaire` SET " +
                        "`prenom_proprietaire` = ?, " +
                        "`nom_proprietaire` = ? " +
                    "WHERE `id_proprietaire` = ? " +
                    "LIMIT 1"
                );
                
                // Then, bind parameters
                query.setString(1, owner.getFirstName());
                query.setString(2, owner.getLastName());
                query.setInt(3, owner.getId());
                
                // Finally, execute it
                query.executeUpdate();
            }
            else
            {
                // Build query
                PreparedStatement query = connection.prepare(
                    "INSERT INTO `proprietaire` " +
                    "(`prenom_proprietaire`, `nom_proprietaire`) " +
                    "VALUES " +
                    "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                );
                
                // Then, bind parameters
                query.setString(1, owner.getFirstName());
                query.setString(2, owner.getLastName());
                
                // Finally, execute it
                query.executeUpdate();
                
                // Fetch the newly generated id
                ResultSet resultSet = query.getGeneratedKeys();
                
                if(resultSet.next())
                {
                    owner.setId(resultSet.getInt(1));
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
     * Deletes a single owner from the database.
     * 
     * @param owner The owner to delete.
     */
    public void delete(Owner owner)
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != owner.getId())
        {
            try
            {
                // Start a transaction
                connection.beginTransaction();
                
                // First, delete the bookings
                PreparedStatement bookingsDeletion = connection.prepare(
                    "DELETE FROM `oeuvrevente` " +
                    "WHERE `id_proprietaire` = ?"
                );
                bookingsDeletion.setInt(1, owner.getId());
                bookingsDeletion.executeUpdate();
                
                // Then, delete the owner themselves
                PreparedStatement ownerDeletion = connection.prepare(
                    "DELETE FROM `proprietaire` " +
                    "WHERE `id_proprietaire` = ? " +
                    "LIMIT 1"
                );
                ownerDeletion.setInt(1, owner.getId());
                ownerDeletion.executeUpdate();
                
                // Commit the transaction
                connection.endTransaction();
            }
            catch(SQLException mainException)
            {
                mainException.printStackTrace();
                
                try
                {
                    connection.cancelTransaction();
                    
                }
                catch(SQLException secondaryException)
                {
                    secondaryException.printStackTrace();
                }
            }
        }
        else
        {
            // @todo Error handling
        }
    }
    
    /**
     * Builds an owner from their data.
     * 
     * @param resultSet Data extracted from the database.
     * @return The newly built owner.
     * @throws SQLException
     */
    protected Owner buildEntity(ResultSet resultSet)
    throws SQLException
    {
        return new Owner(
            resultSet.getInt("id_proprietaire"),
            resultSet.getString("prenom_proprietaire"),
            resultSet.getString("nom_proprietaire")
        );
    }
}
