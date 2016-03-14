package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
@WebServlet(name = "OwnersController", urlPatterns = {"/owners.jsp"})
public class OwnersController extends AbstractController
{
    /**
     * The adherents's controller URL.
     */
    protected static final String URL = "/owners.jsp";
    
    /**
     * The owner's list action parameter's value.
     */
    protected static final String ACTION_LIST = "list";
    
    /**
     * The owner's add action parameter's value.
     */
    protected static final String ACTION_ADD = "add";
    
    /**
     * The owner's edit action parameter's value.
     */
    protected static final String ACTION_EDIT = "edit";
    
    /**
     * The owner's delete action parameter's value.
     */
    protected static final String ACTION_DELETE = "delete";
    
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
            // By default, list all owners
            actionName = OwnersController.ACTION_LIST;
        }
        
        switch(actionName)
        {
            case OwnersController.ACTION_LIST:
                targetPath = this.executeList(request, response);
            break;
                
            case OwnersController.ACTION_ADD:
                targetPath = this.executeAdd(request, response);
            break;
            
            case OwnersController.ACTION_EDIT:
                targetPath = this.executeEdit(request, response);
            break;
            
            case OwnersController.ACTION_DELETE:
                targetPath = this.executeDelete(request, response);
            break;
        }
        
        // Should the request be forwarded?
        if(null != targetPath)
        {
            this.forward(targetPath, request, response);
        }
    }
    
    /**
     * Displays a list of owners.
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
        String targetPath = "/WEB-INF/owners/list.jsp";
        OwnerRepository repository = new OwnerRepository();

        try
        {
            request.setAttribute("owners", repository.fetchAll());
            
            // Display flash messages
            request.setAttribute("_flash", this.getAndClearFlashList(request));
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
     * Displays and handles a form to add an owner.
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
        String targetPath = "/WEB-INF/owners/add.jsp";

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            // Perform some checks
            boolean hasError = false;
            firstName = firstName != null ? firstName.trim() : "";
            lastName = lastName != null ? lastName.trim() : "";

            if(firstName.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_first_name", "Vous devez renseigner le prénom.");
            }

            if(lastName.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_last_name", "Vous devez renseigner le nom.");
            }

            if(!hasError)
            {
                try
                {
                    // Build the new owner
                    Owner owner = new Owner(firstName, lastName);

                    // And save them
                    OwnerRepository repository = new OwnerRepository();
                    repository.save(owner);

                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez crée un nouveau propriétaire nommé <strong>%s %s</strong>.",
                            StringEscapeUtils.escapeHtml4(firstName),
                            StringEscapeUtils.escapeHtml4(lastName)
                        )
                    );
                    
                    // Finally, redirect the user
                    return this.redirect(OwnersController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + OwnersController.ACTION_LIST, request, response);
                }
                catch(NamingException | SQLException e)
                {
                    return this.displayError(
                        "Erreur",
                        "Une erreur est survenue lors de l'ajout d'un nouveau propriétaire.",
                        e,
                        request
                    );
                }
            }

            // The form did have errors
            request.setAttribute("_last_first_name", firstName);
            request.setAttribute("_last_last_name", lastName);
        }
        
        return targetPath;
    }
    
    /**
     * Displays and handles a form to edit an owner.
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
        String targetPath = "/WEB-INF/owners/edit.jsp";

        // Try getting the owner's id
        String idToParse = request.getParameter("id");
        int id;

        try
        {
            if(null == idToParse)
            {
                return this.displayError(
                    "Données manquantes",
                    "Vous devez préciser l'identifiant du propriétaire à éditer.",
                    request
                );
            }
            
            id = Integer.parseInt(idToParse);
        }
        catch(NumberFormatException e)
        {
            return this.displayError(
                "Erreur",
                "Impossible de récupérer l'identifiant du propriétaire.",
                e,
                request
            );
        }
        
        // Fetch the owner
        OwnerRepository repository = new OwnerRepository();
        Owner owner;
        
        try
        {
            owner = repository.fetch(id);
            
            if(null == owner)
            {
                return this.displayError(
                    "Erreur 404",
                    "Ce propriétaire n'existe pas ou plus.",
                    request
                );
            }
        }
        catch(NamingException | SQLException e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue pendant la récupération des informations du propriétaire.",
                e,
                request
            );
        }
        
        // Bind the owner so as to display their data
        request.setAttribute("owner", owner);

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            // Perform some checks
            boolean hasError = false;
            firstName = null != firstName ? firstName.trim() : "";
            lastName = null != lastName ? lastName.trim() : "";

            if(firstName.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_first_name", "Vous devez renseigner le prénom.");
            }

            if(lastName.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_last_name", "Vous devez renseigner le nom.");
            }

            if(!hasError)
            {
                try
                {
                    // Customize flash message
                    String flashMessage = "Vous avez édité avec succès le propriétaire nommé <strong>%s %s</strong>.";
                    
                    if(!owner.getFirstName().equals(firstName) || !owner.getLastName().equals(lastName))
                    {
                        flashMessage = "Vous avez édité avec succès le propriétaire maintenant nommé <strong>%s %s</strong>.";
                    }
                    
                    // Edit the owner
                    owner.setFirstName(firstName);
                    owner.setLastName(lastName);
                    
                    // Then, save them
                    repository.save(owner);

                    // Then, define a flash message to inform the user
                    this.addFlash(request,
                        "success",
                        String.format(
                            flashMessage,
                            StringEscapeUtils.escapeHtml4(owner.getFirstName()),
                            StringEscapeUtils.escapeHtml4(owner.getLastName())
                        )
                    );
                    // Finally, redirect the user
                    return this.redirect(OwnersController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + OwnersController.ACTION_LIST, request, response);
                }
                catch(NamingException | SQLException e)
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
            request.setAttribute("_last_first_name", firstName);
            request.setAttribute("_last_last_name", lastName);
        }
        
        return targetPath;
    }
    
    /**
     * Handles the deletion of an owner.
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
        // Try getting the owner's id
        String idToParse = request.getParameter("id");

        if(null == idToParse)
        {
            return this.displayError(
                "Données manquantes",
                "Vous devez préciser l'identifiant du propriétaire à supprimer.",
                request
            );
        }

        // Get the actual id
        int id = Integer.parseInt(idToParse);
        
        try
        {
            // Fetch the owner
            OwnerRepository repository = new OwnerRepository();
            Owner owner = repository.fetch(id);

            if(null == owner)
            {
                return this.displayError(
                    "Erreur 404",
                    "Ce propriétaire n'existe pas ou plus.",
                    request
                );
            }
            
            // Then, delete them
            repository.delete(owner);
            
            // Then, define a flash message to inform the user
            this.addFlash(request,
                "success",
                String.format("Vous avez supprimé le propriétaire nommé <strong>%s %s</strong>.",
                    StringEscapeUtils.escapeHtml4(owner.getFirstName()),
                    StringEscapeUtils.escapeHtml4(owner.getLastName())
                )
            );
            
            // Finally, redirect the user
            return this.redirect(OwnersController.URL + "?" + AbstractController.PARAMETER_ACTION + "=" + OwnersController.ACTION_LIST, request, response);
        }
        catch(NamingException | SQLException e)
        {
            return this.displayError(
                "Erreur",
                "Une erreur est survenue lors de la suppression d'un propriétaire.",
                e,
                request
            );
        }
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
