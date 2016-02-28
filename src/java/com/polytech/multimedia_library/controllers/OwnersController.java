package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import java.io.IOException;
import java.util.ArrayList;
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
        request.setAttribute("_method", request.getMethod());
        
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
        String targetPath = "/WEB-INF/owners/list.jsp";
        OwnerRepository repository = new OwnerRepository();

        try
        {
            request.setAttribute("owners", repository.fetchAll());
            
            // Display flash messages
            request.setAttribute("_flash", new ArrayList<>(this.getFlashList(request)));
            this.clearFlashList(request);
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
        final String requestMethod = request.getMethod();
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
                    response.sendRedirect("owners.jsp?action=" + OwnersController.ACTION_LIST);

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
        final String requestMethod = request.getMethod();
        String targetPath = "/WEB-INF/owners/edit.jsp";

        // Try getting the owner's id
        String idToParse = request.getParameter("id");

        if(null == idToParse)
        {
            this.displayError("Vous devez préciser l'identifiant du propriétaire à éditer.", request, response);

            return null;
        }

        int id = Integer.parseInt(idToParse);
        
        // Fetch the owner
        OwnerRepository repository = new OwnerRepository();
        Owner owner = repository.fetch(id);
        
        if(null == owner)
        {
            this.displayError("Ce propriétaire n'existe pas ou plus.", request, response);
            
            return null;
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
                    // Edit the owner
                    owner.setFirstName(firstName);
                    owner.setLastName(lastName);
                    
                    // Then, save them
                    repository.save(owner);

                    // Then, define a flash message to inform the user
                    this.addFlash(request,
                        "success",
                        String.format("Vous avez édité avec succès le propriétaire maintenant nommé <strong>%s %s</strong>.",
                            StringEscapeUtils.escapeHtml4(owner.getFirstName()),
                            StringEscapeUtils.escapeHtml4(owner.getLastName())
                        )
                    );
                    // Finally, redirect the user
                    response.sendRedirect("owners.jsp?action=" + OwnersController.ACTION_LIST);

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
        // Initialize vars
        final String requestMethod = request.getMethod();

        // Try getting the owner's id
        String idToParse = request.getParameter("id");

        if(null == idToParse)
        {
            this.displayError("Vous devez préciser l'identifiant du propriétaire à supprimer.", request, response);

            return null;
        }

        int id = Integer.parseInt(idToParse);
        
        // Fetch the owner
        OwnerRepository repository = new OwnerRepository();
        Owner owner = repository.fetch(id);
        
        if(null == owner)
        {
            this.displayError("Ce propriétaire n'existe pas ou plus.", request, response);
            
            return null;
        }
        
        // Then, deleting them
        try
        {
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
            response.sendRedirect("owners.jsp?action=" + OwnersController.ACTION_LIST);
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
