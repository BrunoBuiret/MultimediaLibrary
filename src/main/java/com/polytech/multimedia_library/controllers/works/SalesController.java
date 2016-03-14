package com.polytech.multimedia_library.controllers.works;

import com.polytech.multimedia_library.controllers.AbstractController;
import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.entities.works.SellableWork;
import com.polytech.multimedia_library.entities.works.sellable.Booking;
import com.polytech.multimedia_library.entities.works.sellable.State;
import com.polytech.multimedia_library.entities.works.sellable.bookings.Status;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.AdherentRepository;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import com.polytech.multimedia_library.repositories.works.SellableWorkRepository;
import com.polytech.multimedia_library.utilities.DateUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
@WebServlet(name = "SalesController", urlPatterns = {"/sellableWorks.jsp"})
public class SalesController extends AbstractController
{
    /**
     * The sellable works' controller URL.
     */
    protected static final String URL = "/sellableWorks.jsp";
    
    /**
     * The sellable works' list action parameter's value.
     */
    protected static final String ACTION_LIST = "list";
    
    /**
     * The sellable works' add action parameter's value.
     */
    protected static final String ACTION_ADD = "add";
    
    /**
     * The sellable works' edit action parameter's value.
     */
    protected static final String ACTION_EDIT = "edit";
    
    /**
     * The sellable works' delete action parameter's value.
     */
    protected static final String ACTION_DELETE = "delete";
    
    /**
     * The sellable works' book action parameter's value.
     */
    protected static final String ACTION_BOOK = "book";
    
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
            // By default, list all adherents
            actionName = LoansController.ACTION_LIST;
        }
        
        switch(actionName)
        {
            case SalesController.ACTION_LIST:
                targetPath = this.executeList(request, response);
            break;
                
            case SalesController.ACTION_ADD:
                targetPath = this.executeAdd(request, response);
            break;
            
            case SalesController.ACTION_EDIT:
                targetPath = this.executeEdit(request, response);
            break;
            
            case SalesController.ACTION_DELETE:
                targetPath = this.executeDelete(request, response);
            break;
            
            case SalesController.ACTION_BOOK:
                targetPath = this.executeBook(request, response);
            break;
        }
        
        // Should the request be forwarded?
        if(null != targetPath)
        {
            this.forward(targetPath, request, response);
        }
    }
    
    /**
     * Displays a list of sellable works.
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return The file to forward the request to, or <code>null</code>.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
     */
    protected String executeList(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        String targetPath = "/WEB-INF/works/sales/list.jsp";
        SellableWorkRepository repository = new SellableWorkRepository();

        try
        {
            request.setAttribute("works", repository.fetchAll());
            
            // Display flash messages
            request.setAttribute("_flash", this.getAndClearFlashList(request));
        }
        catch(NamingException | SQLException | ParseException e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la récupération de la liste des oeuvres à vendre.",
                e,
                request
            );
        }
        
        return targetPath;
    }
    
    /**
     * Displays and handles a form to add a sellable work.
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return The file to forward the request to, or <code>null</code>.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
     */
    protected String executeAdd(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        final String requestMethod = request.getMethod().toUpperCase();
        String targetPath = "/WEB-INF/works/sales/add.jsp";
        
        // Initialize additional vars
        OwnerRepository ownerRepository = new OwnerRepository();

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String name = request.getParameter("name");
            String ownerIdToParse = request.getParameter("ownerId");
            String priceToParse = request.getParameter("price");
            
            // Initialize vars
            int ownerId = 0;
            Owner owner = null;
            double price = 0.;

            // Perform some checks
            boolean hasError = false;
            name = name != null ? name.trim() : "";
            ownerIdToParse = ownerIdToParse != null ? ownerIdToParse.trim() : "";
            priceToParse = priceToParse != null ? priceToParse.trim() : "";

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
            
            if(!priceToParse.isEmpty())
            {
                try
                {
                    price = Double.parseDouble(priceToParse);
                    
                    if(price < 0)
                    {
                        hasError = true;
                        request.setAttribute("_error_price", "Le prix de l'oeuvre ne peut pas être négatif.");
                    }
                }
                catch(NumberFormatException e)
                {
                    hasError = true;
                    request.setAttribute("_error_price", "Impossible de récupérer le prix de l'oeuvre.");
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_price", "Vous devez renseigner le prix de l'oeuvre.");
            }

            if(!hasError)
            {
                try
                {
                    // Build the new work
                    SellableWork work = new SellableWork(name, owner, price, State.UNKNOWN);

                    // And save it
                    SellableWorkRepository workRepository = new SellableWorkRepository();
                    workRepository.save(work);

                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez crée une nouvelle oeuvre à vendre nommée <strong>%s</strong>.",
                            StringEscapeUtils.escapeHtml4(name)
                        )
                    );
                    
                    // Finally, redirect the user
                    return this.redirect(SalesController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + SalesController.ACTION_LIST, request, response);
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de l'ajout d'une nouvelle oeuvre à vendre.",
                        e,
                        request
                    );
                }
            }

            // The form did have errors
            request.setAttribute("_last_name", name);
            request.setAttribute("_last_owner_id", ownerId);
            request.setAttribute("_last_price", priceToParse);
        }
        
        // Fetch the existing owners
        try
        {
            request.setAttribute("owners", ownerRepository.fetchAll());
        }
        catch(NamingException | SQLException e)
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
     * Displays and handles a form to edit a sellable work.
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return The file to forward the request to, or <code>null</code>.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
     */
    protected String executeEdit(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        final String requestMethod = request.getMethod().toUpperCase();
        String targetPath = "/WEB-INF/works/sales/edit.jsp";

        // Try getting the owner's id
        String idToParse = request.getParameter("id");
        int id;

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
        SellableWorkRepository workRepository = new SellableWorkRepository();
        SellableWork work;
        
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
        catch(NamingException | SQLException | ParseException e)
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
            String priceToParse = request.getParameter("price");
            
            // Initialize vars
            int ownerId = 0;
            Owner owner = null;
            double price = 0.;

            // Perform some checks
            boolean hasError = false;
            name = null != name ? name.trim() : "";
            ownerIdToParse = null != ownerIdToParse ? ownerIdToParse.trim() : "";
            priceToParse = priceToParse != null ? priceToParse.trim() : "";

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
            
            if(!priceToParse.isEmpty())
            {
                try
                {
                    price = Double.parseDouble(priceToParse);
                    
                    if(price < 0)
                    {
                        hasError = true;
                        request.setAttribute("_error_price", "Le prix de l'oeuvre ne peut pas être négatif.");
                    }
                }
                catch(NumberFormatException e)
                {
                    hasError = true;
                    request.setAttribute("_error_price", "Impossible de récupérer le prix de l'oeuvre.");
                }
            }
            else
            {
                hasError = true;
                request.setAttribute("_error_price", "Vous devez renseigner le prix de l'oeuvre.");
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
                    work.setPrice(price);
                    
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
                    return this.redirect(SalesController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + LoansController.ACTION_LIST, request, response);
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de l'édition d'une oeuvre à vendre.",
                        e,
                        request
                    );
                }
            }
            
            // The form did have errors
            request.setAttribute("_last_name", name);
            request.setAttribute("_last_owner_id", ownerId);
            request.setAttribute("_last_price", priceToParse);
        }
        
        // Fetch the existing owners
        try
        {
            request.setAttribute("owners", ownerRepository.fetchAll());
        }
        catch(NamingException | SQLException e)
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
     * Handles the deletion of a sellable work.
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return The file to forward the request to, or <code>null</code>.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
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
            SellableWorkRepository repository = new SellableWorkRepository();
            SellableWork work = repository.fetch(id);

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
            return this.redirect(SalesController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + SalesController.ACTION_LIST, request, response);
        }
        catch(NamingException | SQLException | ParseException e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la suppression d'une oeuvre.",
                e,
                request
            );
        }
    }
    
    /**
     * Displays and handles a form to book a sellable work.
     * 
     * @param request The servlet request.
     * @param response The servlet response.
     * @return The file to forward the request to, or <code>null</code>.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
     */
    protected String executeBook(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        // Initialize vars
        final String requestMethod = request.getMethod().toUpperCase();
        String targetPath = "/WEB-INF/works/sales/book.jsp";

        // Try getting the work's id
        String idToParse = request.getParameter("id");
        int id;

        try
        {
            if(null == idToParse)
            {
                return this.displayError(
                    "Données manquantes",
                    "Vous devez préciser l'identifiant de l'oeuvre à réserver.",
                    request
                );
            }
            
            id = Integer.parseInt(idToParse);
        }
        catch(NumberFormatException e)
        {
            return this.displayError(
                "Erreur",
                "Impossible de récupérer l'identifiant de l'oeuvre à réserver.",
                e,
                request
            );
        }
        
        // Fetch the work
        SellableWorkRepository workRepository = new SellableWorkRepository();
        SellableWork work;
        
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
            else if(work.hasBooking())
            {
                return this.displayError(
                    "Oeuvre déjà réservée",
                    String.format(
                        "L'oeuvre <strong>%s</strong> a déjà été réservé pour <strong>%s %s</strong>.",
                        StringEscapeUtils.escapeHtml4(work.getName()),
                        StringEscapeUtils.escapeHtml4(work.getBooking().getAdherent().getFirstName()),
                        StringEscapeUtils.escapeHtml4(work.getBooking().getAdherent().getLastName())
                    ),
                    request
                );
            }
        }
        catch(NamingException | SQLException | ParseException e)
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
        
        // Bind additional data for the view and the form handling
        Date dateToday = DateUtils.getToday();
        request.setAttribute("today", dateToday);
        
        // Initialize additional vars
        AdherentRepository adherentRepository = new AdherentRepository();
        
        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String adherentIdToParse = request.getParameter("adherentId");
            
            // Initialize vars
            int adherentId = 0;
            Adherent adherent = null;

            // Perform some checks
            boolean hasError = false;
            adherentIdToParse = adherentIdToParse != null ? adherentIdToParse.trim() : "";
            
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
            
            if(!hasError)
            {
                try
                {
                    // Build the new booking and associate it
                    work.setBooking(new Booking(adherent, dateToday, Status.PENDING));
                    
                    // And save it
                    workRepository.save(work);
                    
                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez réservé <strong>%s</strong> pour <strong>%s %s</strong>.",
                            StringEscapeUtils.escapeHtml4(work.getName()),
                            StringEscapeUtils.escapeHtml4(adherent.getFirstName()),
                            StringEscapeUtils.escapeHtml4(adherent.getLastName())
                        )
                    );
                    
                    // Finally, redirect the user
                    return this.redirect(SalesController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + LoansController.ACTION_LIST, request, response);
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de la réservation d'une oeuvre.",
                        e,
                        request
                    );
                }
            }
            
            // The form did have errors
            request.setAttribute("_last_adherent_id", adherentId);
        }
        
        // Fetch the existing adherents
        try
        {
            request.setAttribute("adherents", adherentRepository.fetchAll());
        }
        catch(NamingException | SQLException e)
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
