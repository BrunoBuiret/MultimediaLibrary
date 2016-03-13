package com.polytech.multimedia_library.controllers.works;

import com.polytech.multimedia_library.controllers.AbstractController;
import com.polytech.multimedia_library.entities.Owner;
import com.polytech.multimedia_library.entities.works.LoanableWork;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import com.polytech.multimedia_library.repositories.works.LoanableWorkRepository;
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
@WebServlet(name = "LoansController", urlPatterns = {"/loanableWorks.jsp"})
public class LoansController extends AbstractController
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
            actionName = LoansController.ACTION_LIST;
        }
        
        System.out.println(String.format(
            "contextPath = %s\n" +
            "requestURI = %s\n" +
            "servletPath = %s\n" +
            "localAddr = %s\n" +
            "localName = %s\n" +
            "protocol = %s\n" +
            "serverName = %s",
            request.getContextPath(),
            request.getRequestURI(),
            request.getServletPath(),
            request.getLocalAddr(),
            request.getLocalName(),
            request.getProtocol(),
            request.getServerName()
        ));
        
        switch(actionName)
        {
            case LoansController.ACTION_LIST:
                targetPath = this.executeList(request, response);
            break;
                
            case LoansController.ACTION_ADD:
                targetPath = this.executeAdd(request, response);
            break;
            
            case LoansController.ACTION_EDIT:
                // targetPath = this.executeEdit(request, response);
            break;
            
            case LoansController.ACTION_DELETE:
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
                    // Build the new adherent
                    LoanableWork work = new LoanableWork(name, owner);
                    
                    System.out.println(work);
                    System.out.println(work.getOwner());

                    // And save them
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
            OwnerRepository repository = new OwnerRepository();
            request.setAttribute("owners", repository.fetchAll());
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
                    "Vous avez supprimé l'adhérent nommé <strong>%s</strong>.",
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
