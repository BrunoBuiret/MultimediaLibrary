package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.Adherent;
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
public class AdherentRepository extends AbstractRepository
{
    /**
     * Fetches a single adherent from the database.
     * 
     * @param id The adherent's id.
     * @return The adherent if they exist, <code>null</code> otherwise.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public Adherent fetch(int id)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_adherent`, `prenom_adherent`, `nom_adherent`, `ville_adherent` " +
            "FROM `adherent` " +
            "WHERE `id_adherent` = ? " +
            "LIMIT 1"
        );

        // Then, bind parameters
        query.setInt(1, id);

        // Finally, execute it
        ResultSet resultSet = query.executeQuery();
        
        return resultSet.next() ? this.buildEntity(resultSet) : null;
    }
    
    /**
     * Fetches every adherent from database.
     * 
     * @return The list of adherents.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public List<Adherent> fetchAll()
    throws NamingException, SQLException
    {
        // Initialize vars
        List<Adherent> adherents = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_adherent`, `prenom_adherent`, `nom_adherent`, `ville_adherent` " +
            "FROM `adherent` "
        );

        // Then, execute it
        ResultSet resultSet = query.executeQuery();

        while(resultSet.next())
        {
            adherents.add(this.buildEntity(resultSet));
        }
        
        return adherents;
    }
    
    /**
     * Saves an adherent into the database by, either inserting them
     * or updating them.
     * 
     * @param adherent The adherent to save.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void save(Adherent adherent)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != adherent.getId())
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "UPDATE `adherent` " +
                "SET " +
                    "`prenom_adherent` = ?, " +
                    "`nom_adherent` = ?, " +
                    "`ville_adherent` = ? " +
                "WHERE `id_adherent` = ? " +
                "LIMIT 1"
            );

            // Then, bind parameters
            query.setString(1, adherent.getFirstName());
            query.setString(2, adherent.getLastName());
            query.setString(3, adherent.getTown());
            query.setInt(4, adherent.getId());

            // Finally, execute it
            query.executeUpdate();
        }
        else
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "INSERT INTO `adherent` " +
                "(`prenom_adherent`, `nom_adherent`, `ville_adherent`) " +
                "VALUES " +
                "(?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            // Then, bind parameters
            query.setString(1, adherent.getFirstName());
            query.setString(2, adherent.getLastName());
            query.setString(3, adherent.getTown());

            // Finally, execute it
            query.executeUpdate();

            // Fetch the newly generated id
            ResultSet resultSet = query.getGeneratedKeys();

            if(resultSet.next())
            {
                adherent.setId(resultSet.getInt(1));
            }
        }
    }
    
    /**
     * Deletes a single adherent from the database.
     * 
     * @param adherent The adherent to delete.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void delete(Adherent adherent)
    throws NamingException, SQLException
    {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != adherent.getId())
        {
            try
            {
                // Start a transaction
                connection.beginTransaction();
                
                // First, delete the bookings
                PreparedStatement bookingsDeletion = connection.prepare(
                    "DELETE FROM `reservation` " +
                    "WHERE `id_adherent` = ?"
                );
                bookingsDeletion.setInt(1, adherent.getId());
                bookingsDeletion.executeUpdate();
                
                // Then, delete the adherent themselves
                PreparedStatement adherentDeletion = connection.prepare(
                    "DELETE FROM `adherent` " +
                    "WHERE `id_adherent` = ? " +
                    "LIMIT 1"
                );
                adherentDeletion.setInt(1, adherent.getId());
                adherentDeletion.executeUpdate();
                
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
                "This adherent cannot be deleted because they don't have an id, " +
                "they might not be registered yet."
            );
        }
    }
    
    /**
     * Builds an adherent from their data.
     * 
     * @param resultSet Data extracted from the database.
     * @return The newly built adherent.
     * @throws SQLException
     */
    protected Adherent buildEntity(ResultSet resultSet)
    throws SQLException
    {
        return new Adherent(
            resultSet.getInt("id_adherent"),
            resultSet.getString("prenom_adherent"),
            resultSet.getString("nom_adherent"),
            resultSet.getString("ville_adherent")
        );
    }
}
