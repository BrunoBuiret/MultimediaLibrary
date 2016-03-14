package com.polytech.multimedia_library.repositories;

import com.polytech.multimedia_library.database.DatabaseConnection;
import com.polytech.multimedia_library.entities.Loan;
import com.polytech.multimedia_library.repositories.works.LoanableWorkRepository;
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
 *
 * @author bruno
 */
public class LoansRepository extends AbstractRepository
{
    /**
     * 
     * @param id
     * @return 
     */
    public Loan fetch(int id)
    {
        throw new UnsupportedOperationException("This method hasn't been implemented yet.");
    }
    
    /**
     * 
     * @return 
     */
    public List<Loan> fetchAll()
    {
        throw new UnsupportedOperationException("This method hasn't been implemented yet.");
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws NamingException
     * @throws SQLException
     * @throws ParseException 
     */
    public List<Loan> fetchByWorkAndNotFinished(int id)
    throws NamingException, SQLException, ParseException
    {
        // Initialize vars
        List<Loan> loans = new ArrayList<>();
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        // Build query
        PreparedStatement query = connection.prepare(
            "SELECT `id_oeuvrepret`, `id_adherent`, `date_debut`, `date_fin` " +
            "FROM `emprunt` " +
            "WHERE " +
                "`id_oeuvrepret` = ? " +
                "AND " +
                "CURDATE() <= `date_fin`"
        );
        query.setInt(1, id);

        // Then, execute it
        ResultSet resultSet = query.executeQuery();

        while(resultSet.next())
        {
            loans.add(this.buildEntity(resultSet));
        }
        
        return loans;
    }
    
    /**
     * Saves a loan into the database by, either inserting it
     * or updating it.
     * 
     * @param loan The loan to save.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     */
    public void save(Loan loan)
    throws NamingException, SQLException
    {
        // Initialize vars
        DatabaseConnection connection = DatabaseConnection.getInstance();
        
        if(0 != loan.getId())
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "UPDATE `emprunt` " +
                "SET " +
                    "`id_adherent` = ?, " +
                    "`id_oeuvrepret` = ?, " +
                    "`date_debut` = ?, " +
                    "`date_fin` = ? " +
                "WHERE `id_emprunt` = ? " +
                "LIMIT 1"
            );

            // Then, bind parameters
            query.setInt(1, loan.getAdherent().getId());
            query.setInt(2, loan.getWork().getId());
            query.setString(3, DateUtils.toSqlDate(loan.getDateStart()));
            query.setString(4, DateUtils.toSqlDate(loan.getDateEnd()));
            query.setInt(5, loan.getId());

            // Finally, execute it
            query.executeUpdate();
        }
        else
        {
            // Build query
            PreparedStatement query = connection.prepare(
                "INSERT INTO `emprunt` " +
                "(`id_adherent`, `id_oeuvrepret`, `date_debut`, `date_fin`) " +
                "VALUES " +
                "(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            // Then, bind parameters
            query.setInt(1, loan.getAdherent().getId());
            query.setInt(2, loan.getWork().getId());
            query.setString(3, DateUtils.toSqlDate(loan.getDateStart()));
            query.setString(4, DateUtils.toSqlDate(loan.getDateEnd()));

            // Finally, execute it
            query.executeUpdate();

            // Fetch the newly generated id
            ResultSet resultSet = query.getGeneratedKeys();

            if(resultSet.next())
            {
                loan.setId(resultSet.getInt(1));
            }
        }
    }
    
    /**
     * 
     * @param loan 
     */
    public void delete(Loan loan)
    {
        throw new UnsupportedOperationException("This method hasn't been implemented yet.");
    }
    
    /**
     * Builds a loan from their data.
     * 
     * @param resultSet Data extracted from the database.
     * @return The newly built loan.
     * @throws javax.naming.NamingException If the context can't be read.
     * @throws java.sql.SQLException If an SQL error happens.
     * @throws java.text.ParseException
     */
    protected Loan buildEntity(ResultSet resultSet)
    throws NamingException, SQLException, ParseException
    {
        AdherentRepository adherentRepository = new AdherentRepository();
        LoanableWorkRepository workRepository = new LoanableWorkRepository();
        
        return new Loan(
            workRepository.fetch(resultSet.getInt("id_oeuvrepret")),
            adherentRepository.fetch(resultSet.getInt("id_adherent")),
            DateUtils.parseSqlDate(resultSet.getString("date_debut")),
            DateUtils.parseSqlDate(resultSet.getString("date_fin"))
        );
    }
}
