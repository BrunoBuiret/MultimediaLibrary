package com.polytech.multimedia_library.controllers.works;

import com.polytech.multimedia_library.controllers.AbstractController;
import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.Loan;
import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.entities.works.LoanableWork;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.AdherentRepository;
import com.polytech.multimedia_library.repositories.LoansRepository;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import com.polytech.multimedia_library.repositories.works.LoanableWorkRepository;
import com.polytech.multimedia_library.utilities.DateUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
@WebServlet(name = "LoansController", urlPatterns = {"/loanableWorks.jsp"})
public class LoansController extends AbstractController
{
    protected static final String ACTION_LIST = "list";
    
    protected static final String ACTION_ADD = "add";
    
    protected static final String ACTION_EDIT = "edit";
    
    protected static final String ACTION_DELETE = "delete";
    
    protected static final String ACTION_BORROW = "borrow";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize some common attributes
        request.setAttribute("_method", request.getMethod().toUpperCase());
        
        // What is being done?
        String actionName = request.getParameter("action");
        String targetPath = null;
        
        if(null == actionName)
        {
            // By default, list all works
            actionName = LoansController.ACTION_LIST;
        }
        
        switch(actionName)
        {
            case LoansController.ACTION_LIST:
                targetPath = this.executeList(request, response);
            break;
                
            case LoansController.ACTION_ADD:
                targetPath = this.executeAdd(request, response);
            break;
            
            case LoansController.ACTION_EDIT:
                targetPath = this.executeEdit(request, response);
            break;
            
            case LoansController.ACTION_DELETE:
                targetPath = this.executeDelete(request, response);
            break;
            
            case LoansController.ACTION_BORROW:
                targetPath = this.executeBorrow(request, response);
            break;
        }
        
        // Should the request be forwarded?
        if(null != targetPath)
        {
            this.forward(targetPath, request, response);
        }
    }
    
    /**
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    protected String executeList(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        String targetPath = "/WEB-INF/works/loans/list.jsp";
        LoanableWorkRepository repository = new LoanableWorkRepository();

        try
        {
            request.setAttribute("works", repository.fetchAll());
            
            // Display flash messages
            request.setAttribute("_flash", this.getAndClearFlashList(request));
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la récupération de la liste des oeuvres à prêter.",
                e,
                request
            );
        }
        
        return targetPath;
    }
    
    /**
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    protected String executeAdd(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        final String requestMethod = request.getMethod().toUpperCase();
        String targetPath = "/WEB-INF/works/loans/add.jsp";

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String name = request.getParameter("name");
            String ownerIdToParse = request.getParameter("ownerId");
            
            // Initialize vars
            int ownerId = 0;
            OwnerRepository ownerRepository = new OwnerRepository();
            Owner owner = null;

            // Perform some checks
            boolean hasError = false;
            name = name != null ? name.trim() : "";
            ownerIdToParse = ownerIdToParse != null ? ownerIdToParse.trim() : "";

            if(name.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_name", "Vous devez renseigner le nom de l'oeuvre.");
            }

            if(!ownerIdToParse.isEmpty())
            {
                try
                {
                    ownerId = Integer.parseInt(ownerIdToParse);
                    owner = ownerRepository.fetch(ownerId);
                    
                    if(null == owner)
                    {
                        hasError = true;
                        request.setAttribute("_error_owner_id", "Le propriétaire que vous avez renseigné n'existe pas ou plus.");
                    }
                }
                catch(NumberFormatException e)
                {
                    hasError = true;
                    request.setAttribute("_error_owner_id", "Impossible de récupérer l'identifiant du propriétaire de l'oeuvre.");
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur", 
                        "Une erreur est survenue lors de la vérification de l'existence du propriétaire.",
                        e,
                        request
                    );
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_owner_id", "Vous devez renseigner le propriétaire de l'oeuvre.");
            }

            if(!hasError)
            {
                try
                {
                    // Build the new work
                    LoanableWork work = new LoanableWork(name, owner);

                    // And save it
                    LoanableWorkRepository workRepository = new LoanableWorkRepository();
                    workRepository.save(work);

                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez crée une nouvelle oeuvre à prêter nommée <strong>%s</strong>.",
                            StringEscapeUtils.escapeHtml4(name)
                        )
                    );
                    
                    // Finally, redirect the user
                    this.redirect("loanableWorks.jsp?action=" + LoansController.ACTION_LIST, request, response);

                    return null;
                }
                catch(Exception e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de l'ajout d'une nouvelle oeuvre à prêter.",
                        e,
                        request
                    );
                }
            }

            // The form did have errors
            request.setAttribute("_last_name", name);
            request.setAttribute("_last_owner_id", ownerId);
        }
        
        // Fetch the existing owners
        try
        {
            OwnerRepository ownerRepository = new OwnerRepository();
            request.setAttribute("owners", ownerRepository.fetchAll());
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la récupération de la liste des propriétaires.",
                e,
                request
            );
        }
        
        return targetPath;
    }
    
    /**
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    protected String executeEdit(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        final String requestMethod = request.getMethod().toUpperCase();
        String targetPath = "/WEB-INF/works/loans/edit.jsp";

        // Try getting the owner's id
        String idToParse = request.getParameter("id");
        int id = 0;

        try
        {
            if(null == idToParse)
            {
                return this.displayError(
                    "Données manquantes",
                    "Vous devez préciser l'identifiant de l'oeuvre à éditer.",
                    request
                );
            }
            
            id = Integer.parseInt(idToParse);
        }
        catch(NumberFormatException e)
        {
            return this.displayError(
                "Erreur",
                "Impossible de récupérer l'identifiant de l'oeuvre.",
                e,
                request
            );
        }
        
        // Fetch the work
        LoanableWorkRepository workRepository = new LoanableWorkRepository();
        LoanableWork work = null;
        
        try
        {
            work = workRepository.fetch(id);
            
            if(null == work)
            {
                return this.displayError(
                    "Erreur 404",
                    "Cette oeuvre n'existe pas ou plus.",
                    request
                );
            }
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue pendant la récupération des informations de l'oeuvre.",
                e,
                request
            );
        }
        
        // Bind the owner so as to display their data
        request.setAttribute("work", work);
        
        // Initialize additional vars
        OwnerRepository ownerRepository = new OwnerRepository();

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String name = request.getParameter("name");
            String ownerIdToParse = request.getParameter("ownerId");
            
            // Initialize vars
            int ownerId = 0;
            Owner owner = null;

            // Perform some checks
            boolean hasError = false;
            name = null != name ? name.trim() : "";
            ownerIdToParse = null != ownerIdToParse ? ownerIdToParse.trim() : "";

            if(name.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_name", "Vous devez renseigner le prénom.");
            }

            if(!ownerIdToParse.isEmpty())
            {
                try
                {
                    ownerId = Integer.parseInt(ownerIdToParse);
                    owner = ownerRepository.fetch(ownerId);
                    
                    if(null == owner)
                    {
                        hasError = true;
                        request.setAttribute("_error_owner_id", "Le propriétaire que vous avez renseigné n'existe pas ou plus.");
                    }
                }
                catch(NumberFormatException e)
                {
                    hasError = true;
                    request.setAttribute("_error_owner_id", "Impossible de récupérer l'identifiant du propriétaire de l'oeuvre.");
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur", 
                        "Une erreur est survenue lors de la vérification de l'existence du propriétaire.",
                        e,
                        request
                    );
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_owner_id", "Vous devez renseigner le nom.");
            }

            if(!hasError)
            {
                try
                {
                    // Customize flash message
                    String flashMessage = "Vous avez édité avec succès l'oeuvre nommée <strong>%s</strong>.";
                    
                    if(!work.getName().equals(name))
                    {
                        flashMessage = "Vous avez édité avec succès l'oeuvre maintenant nommée <strong>%s</strong>.";
                    }
                    
                    // Edit the work
                    work.setName(name);
                    work.setOwner(owner);
                    
                    // Then, save it
                    workRepository.save(work);

                    // Then, define a flash message to inform the user
                    this.addFlash(request,
                        "success",
                        String.format(
                            flashMessage,
                            StringEscapeUtils.escapeHtml4(work.getName())
                        )
                    );
                    // Finally, redirect the user
                    this.redirect("loanableWorks.jsp?action=" + LoansController.ACTION_LIST, request, response);

                    return null;
                }
                catch(Exception e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de l'édition d'un propriétaire.",
                        e,
                        request
                    );
                }
            }
            
            // If the form wasn't correctly filled, memorize the current values
            request.setAttribute("_last_name", name);
            request.setAttribute("_last_owner_id", ownerId);
        }
        
        // Fetch the existing owners
        try
        {
            request.setAttribute("owners", ownerRepository.fetchAll());
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la récupération de la liste des propriétaires.",
                e,
                request
            );
        }
        
        return targetPath;
    }
    
    /**
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    protected String executeDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Try getting the work's id
        String idToParse = request.getParameter("id");

        if(null == idToParse)
        {
            return this.displayError(
                "Données manquantes",
                "Vous devez préciser l'identifiant de l'oeuvre à supprimer.",
                request
            );
        }

        // Get the actual id
        int id = Integer.parseInt(idToParse);
        
        try
        {
            // Fetch the work
            LoanableWorkRepository repository = new LoanableWorkRepository();
            LoanableWork work = repository.fetch(id);

            if(null == work)
            {
                return this.displayError(
                    "Erreur 404",
                    "Cette oeuvre n'existe pas ou plus.",
                    request
                );
            }
            
            // Then, delete them
            repository.delete(work);
            
            // Then, define a flash message to inform the user
            this.addFlash(
                request,
                "success",
                String.format(
                    "Vous avez supprimé l'oeuvre nommée <strong>%s</strong>.",
                    StringEscapeUtils.escapeHtml4(work.getName())
                )
            );
            
            // Finally, redirect the user
            this.redirect("loanableWorks.jsp?action=" + LoansController.ACTION_LIST, request, response);
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la suppression d'une oeuvre.",
                e,
                request
            );
        }

        return null;
    }
    
    /**
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    protected String executeBorrow(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        final String requestMethod = request.getMethod().toUpperCase();
        String targetPath = "/WEB-INF/works/loans/borrow.jsp";

        // Try getting the owner's id
        String idToParse = request.getParameter("id");
        int id = 0;

        try
        {
            if(null == idToParse)
            {
                return this.displayError(
                    "Données manquantes",
                    "Vous devez préciser l'identifiant de l'oeuvre à emprunter.",
                    request
                );
            }
            
            id = Integer.parseInt(idToParse);
        }
        catch(NumberFormatException e)
        {
            return this.displayError(
                "Erreur",
                "Impossible de récupérer l'identifiant de l'oeuvre à emprunter.",
                e,
                request
            );
        }
        
        // Fetch the work
        LoanableWorkRepository workRepository = new LoanableWorkRepository();
        LoanableWork work = null;
        
        try
        {
            work = workRepository.fetch(id);
            
            if(null == work)
            {
                return this.displayError(
                    "Erreur 404",
                    "Cette oeuvre n'existe pas ou plus.",
                    request
                );
            }
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue pendant la récupération des informations de l'oeuvre.",
                e,
                request
            );
        }
        
        // Bind the work so as to display its data
        request.setAttribute("work", work);
        
        // Fetch the existing loans so as to avoid overlapping
        LoansRepository loanRepository = new LoansRepository();
        List<Loan> loans = null;
        Set<Date> loanDates = new HashSet<>();
        
        try
        {
            loans = loanRepository.fetchByWorkAndNotFinished(id);
            
            for(Loan loan : loans)
            {
                loanDates.addAll(DateUtils.getDaysBetween(loan.getDateStart(), loan.getDateEnd()));
            }
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue pendant la récupération des prêts existant de l'oeuvre.",
                e,
                request
            );
        }
        
        // Bind the work so as to display its data
        request.setAttribute("loanDates", loanDates);
        
        // Initialize additional vars
        AdherentRepository adherentRepository = new AdherentRepository();
        
        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String adherentIdToParse = request.getParameter("adherentId");
            String dateStartToParse = request.getParameter("dateStart");
            String dateEndToParse = request.getParameter("dateEnd");
            
            // Initialize vars
            int adherentId = 0;
            Adherent adherent = null;
            Date dateStart = null, dateEnd = null, dateNow = DateUtils.getToday();
            
            // Perform some checks
            boolean hasError = false;
            adherentIdToParse = null != adherentIdToParse ? adherentIdToParse.trim() : "";
            dateStartToParse = null != dateStartToParse ? dateStartToParse.trim() : "";
            dateEndToParse = null != dateEndToParse ? dateEndToParse.trim() : "";
            
            if(!adherentIdToParse.isEmpty())
            {
                try
                {
                    adherentId = Integer.parseInt(adherentIdToParse);
                    adherent = adherentRepository.fetch(adherentId);
                    
                    // Does the adherent exist?
                    if(null == adherent)
                    {
                        hasError = true;
                        request.setAttribute("_error_adherent_id", "L'adhérent que vous avez renseigné n'existe pas ou plus.");
                    }
                }
                catch(NumberFormatException e)
                {
                    hasError = true;
                    request.setAttribute("_error_adherent_id", "Impossible de récupérer l'identifiant de l'adhérent.");
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur", 
                        "Une erreur est survenue lors de la vérification de l'existence de l'adhérent.",
                        e,
                        request
                    );
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_adherent_id", "Vous devez renseigner l'adhérent souhaitant emprunter l'oeuvre.");
            }
            
            if(!dateStartToParse.isEmpty())
            {
                try
                {
                    dateStart = DateUtils.parseFormDate(dateStartToParse);
                    
                    // Is the date in the past?
                    if(dateStart.before(dateNow))
                    {
                        hasError = true;
                        request.setAttribute("_error_date_start", "Impossible d'emprunter une oeuvre avant aujourd'hui.");
                    }
                }
                catch(ParseException e)
                {
                    hasError = true;
                    request.setAttribute("_error_date_start", "Impossible d'interpréter la date de début donnée.");
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_date_start", "Vous devez renseigner la date de début de l'emprunt.");
            }
            
            if(!dateEndToParse.isEmpty())
            {
                try
                {
                    dateEnd = DateUtils.parseFormDate(dateEndToParse);
                    
                    // Is the date in the past?
                    if(dateEnd.before(dateNow))
                    {
                        hasError = true;
                        request.setAttribute("_error_date_end", "Impossible d'emprunter une oeuvre avant aujourd'hui.");
                    }
                    // Or is the end before the start?
                    else if(null != dateStart && dateStart.after(dateEnd))
                    {
                        hasError = true;
                        request.setAttribute("_error_date_end", "La date de fin ne peut pas se trouver avant la date de début.");
                    }
                }
                catch(ParseException e)
                {
                    hasError = true;
                    request.setAttribute("_error_date_end", "Impossible d'interpréter la date de fin donnée.");
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_date_end", "Vous devez renseigner la date de fin de l'emprunt.");
            }
            
            // Is the work already borrowed for those dates?
            if(null != dateStart && null != dateEnd)
            {
                if(loanDates.contains(dateStart))
                {
                    hasError = true;
                    request.setAttribute("_error_date_start", "Cette oeuvre est déjà empruntée ce jour-ci.");
                }
                
                if(loanDates.contains(dateEnd))
                {
                    hasError = true;
                    request.setAttribute("_error_date_end", "Cette oeuvre est déjà empruntée ce jour-ci.");
                }
            }
            
            if(!hasError)
            {
                try
                {
                    // Build the new loan
                    Loan loan = new Loan(work, adherent, dateStart, dateEnd);
                    
                    // And save it
                    loanRepository.save(loan);
                    
                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez ajouté un emprunt pour l'oeuvre nommée <strong>%s</strong>.",
                            StringEscapeUtils.escapeHtml4(work.getName())
                        )
                    );
                    
                    // Finally, redirect the user
                    this.redirect("loanableWorks.jsp?action=" + LoansController.ACTION_LIST, request, response);

                    return null;
                }
                catch(Exception e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de l'ajout de l'emprunt d'une oeuvre.",
                        e,
                        request
                    );
                }
            }
            
            // The form did have errors
            request.setAttribute("_last_adherent_id", adherentId);
            request.setAttribute("_last_date_start", dateStartToParse);
            request.setAttribute("_last_date_end", dateEndToParse);
        }
        
        // Fetch the existing adherents
        try
        {
            request.setAttribute("adherents", adherentRepository.fetchAll());
        }
        catch(Exception e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la récupération de la liste des adhérents.",
                e,
                request
            );
        }
        
        return targetPath;
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        this.processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
}
