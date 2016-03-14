package com.polytech.multimedia_library.repositories.works;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.works.SellableWork;
import com.polytech.multimedia_library.entities.works.sellable.Booking;
import com.polytech.multimedia_library.entities.works.sellable.State;
import com.polytech.multimedia_library.entities.works.sellable.bookings.Status;
import com.polytech.multimedia_library.repositories.AdherentRepository;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import com.polytech.multimedia_library.utilities.DateUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
     * @throws java.text.ParseException
     */
    public SellableWork fetch(int id)
    throws NamingException, SQLException, ParseException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT " +
                "o.`id_oeuvrevente`, o.`titre_oeuvrevente`, o.`etat_oeuvrevente`, " +
                "o.`prix_oeuvrevente`, o.`id_proprietaire`, " +
                "r.`id_adherent`, r.`date_reservation`, r.`statut` " +
            "FROM `oeuvrevente` o " +
            "LEFT JOIN `reservation` r " +
            "ON r.id_oeuvrevente = o.id_oeuvrevente " +
            "WHERE o.`id_oeuvrevente` = ? " +
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
     * @throws java.text.ParseException
     */
    public List<SellableWork> fetchAll()
    throws NamingException, SQLException, ParseException
    {
        // Initialize vars
        List<SellableWork> sellableWorks = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT " +
                "o.`id_oeuvrevente`, o.`titre_oeuvrevente`, o.`etat_oeuvrevente`, " +
                "o.`prix_oeuvrevente`, o.`id_proprietaire`, " +
                "r.`id_adherent`, r.`date_reservation`, r.`statut` " +
            "FROM `oeuvrevente` o " +
            "LEFT JOIN `reservation` r " +
            "ON r.id_oeuvrevente = o.id_oeuvrevente"
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
            try
            {
                // Start a transaction
                connection.beginTransaction();
                
                // First, update the work
                PreparedStatement workQuery = connection.prepare(
                    "UPDATE `oeuvrevente` " +
                    "SET " +
                        "`titre_oeuvrevente` = ?, " +
                        "`etat_oeuvrevente` = ?, " +
                        "`prix_oeuvrevente` = ?, " +
                        "`id_proprietaire` = ? " +
                    "WHERE `id_oeuvrevente` = ? " +
                    "LIMIT 1"
                );
                workQuery.setString(1, work.getName());
                workQuery.setString(2, work.getState().getCode());
                workQuery.setDouble(3, work.getPrice());
                workQuery.setInt(4, work.getOwner().getId());
                workQuery.setInt(5, work.getId());
                workQuery.executeUpdate();
                
                // Then, the booking
                if(work.hasBooking())
                {
                    System.out.println("Coucou");
                    
                    PreparedStatement bookingQuery = connection.prepare(
                        "INSERT INTO `reservation` " +
                        "(`id_oeuvrevente`, `id_adherent`, `date_reservation`, `statut`) " +
                        "VALUES " +
                        "(?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "`date_reservation` = VALUES(`date_reservation`), " +
                            "`statut` = VALUES(`statut`)"
                    );
                    bookingQuery.setInt(1, work.getId());
                    bookingQuery.setInt(2, work.getBooking().getAdherent().getId());
                    bookingQuery.setString(3, DateUtils.toSqlDate(work.getBooking().getDate()));
                    bookingQuery.setString(4, work.getBooking().getStatus().getCode());
                    bookingQuery.executeUpdate();
                }

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
            query.setString(2, work.getState().getCode());
            query.setDouble(3, work.getPrice());
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
     * @throws java.text.ParseException
     */
    protected SellableWork buildEntity(ResultSet resultSet)
    throws NamingException, SQLException, ParseException
    {
        OwnerRepository ownerRepository = new OwnerRepository();
        
        if(
            0 != resultSet.getInt("id_adherent")
            && null != resultSet.getString("date_reservation")
            && null != resultSet.getString("statut")
        )
        {
            AdherentRepository adherentRepository = new AdherentRepository();
            
            return new SellableWork(
                resultSet.getInt("id_oeuvrevente"),
                resultSet.getString("titre_oeuvrevente"),
                ownerRepository.fetch(resultSet.getInt("id_proprietaire")),
                resultSet.getDouble("prix_oeuvrevente"),
                State.fromCode(resultSet.getString("etat_oeuvrevente")),
                new Booking(
                    adherentRepository.fetch(resultSet.getInt("id_adherent")),
                    DateUtils.parseSqlDate(resultSet.getString("date_reservation")),
                    Status.fromCode(resultSet.getString("statut"))
                )
            );
        }
        else
        {
            return new SellableWork(
                resultSet.getInt("id_oeuvrevente"),
                resultSet.getString("titre_oeuvrevente"),
                ownerRepository.fetch(resultSet.getInt("id_proprietaire")),
                resultSet.getDouble("prix_oeuvrevente"),
                State.fromCode(resultSet.getString("etat_oeuvrevente"))
            );
        }
    }
}
