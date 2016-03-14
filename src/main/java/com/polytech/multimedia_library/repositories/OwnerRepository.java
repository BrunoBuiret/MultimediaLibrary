package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.Owner;
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
public class OwnerRepository extends AbstractRepository
{
    /**
     * Fetches a single owner from the database.
     * 
     * @param id The owner's id.
     * @return The owner if they exist, <code>null</code> otherwise.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public Owner fetch(int id)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
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
        
        return resultSet.next() ? this.buildEntity(resultSet) : null;
    }
    
    /**
     * Fetches every owner from database.
     * 
     * @return The list of owner.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public List<Owner> fetchAll()
    throws NamingException, SQLException
    {
        // Initialize vars
        List<Owner> owners = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
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
        
        return owners;
    }
    
    /**
     * Saves an owner into the database by, either inserting them
     * or updating them.
     * 
     * @param owner The owner to save.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void save(Owner owner)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != owner.getId())
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "UPDATE `proprietaire` " +
                "SET " +
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
    
    /**
     * Deletes a single owner from the database.
     * 
     * @param owner The owner to delete.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void delete(Owner owner)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != owner.getId())
        {
            try
            {
                // Start a transaction
                connection.beginTransaction();
                
                // First, delete the sales
                PreparedStatement salesDeletion = connection.prepare(
                    "DELETE FROM `reservation` " +
                    "WHERE `id_oeuvrevente` IN (" +
                        "SELECT `id_oeuvrevente` " +
                        "FROM `oeuvrevente` " +
                        "WHERE `id_proprietaire` = ? " +
                    ")"
                );
                salesDeletion.setInt(1, owner.getId());
                salesDeletion.executeUpdate();
                
                // Then, delete the sellable works
                PreparedStatement sellableWorksDeletion = connection.prepare(
                    "DELETE FROM `oeuvrevente` " +
                    "WHERE `id_proprietaire` = ?"
                );
                sellableWorksDeletion.setInt(1, owner.getId());
                sellableWorksDeletion.executeUpdate();
                
                // First, delete the loans
                PreparedStatement loansDeletion = connection.prepare(
                    "DELETE FROM `emprunt` " +
                    "WHERE `id_oeuvrepret` IN (" +
                        "SELECT `id_oeuvrepret` " +
                        "FROM `oeuvrepret` " +
                        "WHERE `id_proprietaire` = ? " +
                    ")"
                );
                loansDeletion.setInt(1, owner.getId());
                loansDeletion.executeUpdate();
                
                // Then, delete the loanable works
                PreparedStatement loanableWorksDeletion = connection.prepare(
                    "DELETE FROM `oeuvrepret` " +
                    "WHERE `id_proprietaire` = ?"
                );
                loanableWorksDeletion.setInt(1, owner.getId());
                loanableWorksDeletion.executeUpdate();
                
                // Finally, delete the owner themselves
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
                "This owner cannot be deleted because they don't have an id, " +
                "they might not be registered yet."
            );
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
