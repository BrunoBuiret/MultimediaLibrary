package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.AdherentRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
@WebServlet(name = "AdherentsController", urlPatterns = {"/adherents.jsp"})
public class AdherentsController extends AbstractController
{
    protected static final String ACTION_LIST = "list";
    
    protected static final String ACTION_ADD = "add";
    
    protected static final String ACTION_EDIT = "edit";
    
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
            // By default, list all adherents
            actionName = AdherentsController.ACTION_LIST;
        }
        
        switch(actionName)
        {
            case AdherentsController.ACTION_LIST:
                targetPath = this.executeList(request, response);
            break;
                
            case AdherentsController.ACTION_ADD:
                targetPath = this.executeAdd(request, response);
            break;
            
            case AdherentsController.ACTION_EDIT:
                targetPath = this.executeEdit(request, response);
            break;
            
            case AdherentsController.ACTION_DELETE:
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
        String targetPath = "/WEB-INF/adherents/list.jsp";
        AdherentRepository repository = new AdherentRepository();

        try
        {
            request.setAttribute("adherents", repository.fetchAll());
            
            // Display flash messages
            request.setAttribute("_flash", this.getAndClearFlashList(request));
        }
        catch(Exception e)
        {
            // @todo Error handling
            e.printStackTrace();
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
        String targetPath = "/WEB-INF/adherents/add.jsp";

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String town = request.getParameter("town");

            // Perform some checks
            boolean hasError = false;
            firstName = firstName != null ? firstName.trim() : "";
            lastName = lastName != null ? lastName.trim() : "";
            town = town != null ? town.trim() : "";

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

            if(town.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_town", "Vous devez renseigner la ville.");
            }

            if(!hasError)
            {
                try
                {
                    // Build the new adherent
                    Adherent adherent = new Adherent(firstName, lastName, town);

                    // And save them
                    AdherentRepository repository = new AdherentRepository();
                    repository.save(adherent);

                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez crée un nouvel adhérent nommé <strong>%s %s</strong>.",
                            StringEscapeUtils.escapeHtml4(firstName),
                            StringEscapeUtils.escapeHtml4(lastName)
                        )
                    );
                    
                    // Finally, redirect the user
                    this.redirect("/adherents.jsp?action=" + AdherentsController.ACTION_LIST, response);

                    return null;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            // The form did have errors
            request.setAttribute("_last_first_name", firstName);
            request.setAttribute("_last_last_name", lastName);
            request.setAttribute("_last_town", town);
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
        String targetPath = "/WEB-INF/adherents/edit.jsp";

        // Try getting the adherent's id
        String idToParse = request.getParameter("id");

        if(null == idToParse)
        {
            this.displayError("Vous devez préciser l'identifiant de l'adhérent à éditer.", request, response);

            return null;
        }

        int id = Integer.parseInt(idToParse);
        
        // Fetch the adherent
        AdherentRepository repository = new AdherentRepository();
        Adherent adherent = repository.fetch(id);
        
        if(null == adherent)
        {
            this.displayError("Cet adhérent n'existe pas ou plus.", request, response);
            
            return null;
        }
        
        // Bind the adherent so as to display their data
        request.setAttribute("adherent", adherent);

        if(requestMethod.equals(HttpProtocol.METHOD_POST))
        {
            // The form has been sent already
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String town = request.getParameter("town");

            // Perform some checks
            boolean hasError = false;
            firstName = null != firstName ? firstName.trim() : "";
            lastName = null != lastName ? lastName.trim() : "";
            town = null != town ? town.trim() : "";

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

            if(town.isEmpty())
            {
                hasError = true;
                request.setAttribute("_error_town", "Vous devez renseigner la ville.");
            }

            if(!hasError)
            {
                try
                {
                    // Customize the flash message
                    String flashMessage = "Vous avez édité avec succès l'adhérent nommé <strong>%s %s</strong>.";
                    
                    if(!adherent.getFirstName().equals(firstName) || !adherent.getLastName().equals(lastName))
                    {
                        flashMessage = "Vous avez édité avec succès l'adhérent maintenant nommé <strong>%s %s</strong>.";
                    }
                    
                    // Edit the adherent
                    adherent.setFirstName(firstName);
                    adherent.setLastName(lastName);
                    adherent.setTown(town);
                    
                    // Then, save them
                    repository.save(adherent);

                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            flashMessage,
                            StringEscapeUtils.escapeHtml4(adherent.getFirstName()),
                            StringEscapeUtils.escapeHtml4(adherent.getLastName())
                        )
                    );
                    // Finally, redirect the user
                    this.redirect("/adherents.jsp?action=" + AdherentsController.ACTION_LIST, response);

                    return null;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            
            // If the form wasn't correctly filled, memorize the current values
            request.setAttribute("_last_first_name", firstName);
            request.setAttribute("_last_last_name", lastName);
            request.setAttribute("_last_town", town);
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
        // Try getting the adherent's id
        String idToParse = request.getParameter("id");

        if(null == idToParse)
        {
            this.displayError("Vous devez préciser l'identifiant de l'adhérent à supprimer.", request, response);

            return null;
        }

        int id = Integer.parseInt(idToParse);
        
        // Fetch the adherent
        AdherentRepository repository = new AdherentRepository();
        Adherent adherent = repository.fetch(id);
        
        if(null == adherent)
        {
            this.displayError("Cet adhérent n'existe pas ou plus.", request, response);
            
            return null;
        }
        
        // Then, deleting them
        try
        {
            repository.delete(adherent);
            // Then, define a flash message to inform the user
            this.addFlash(
                request,
                "success",
                String.format(
                    "Vous avez supprimé l'adhérent nommé <strong>%s %s</strong>.",
                    StringEscapeUtils.escapeHtml4(adherent.getFirstName()),
                    StringEscapeUtils.escapeHtml4(adherent.getLastName())
                )
            );
            
            // Finally, redirect the user
            this.redirect("/adherents.jsp?action=" + AdherentsController.ACTION_LIST, response);
        }
        catch(Exception e)
        {
            // @todo Error handling.
            e.printStackTrace();
        }

        return null;
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
