package com.polytech.multimedia_library.controllers.works;

import com.polytech.multimedia_library.controllers.AbstractController;
import com.polytech.multimedia_library.entities.works.LoanableWork;
import com.polytech.multimedia_library.http.HttpProtocol;
import com.polytech.multimedia_library.repositories.works.LoanableWorkRepository;
import java.io.IOException;
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
                // targetPath = this.executeDelete(request, response);
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
                    LoanableWork work = new LoanableWork();

                    // And save them
                    LoanableWorkRepository repository = new LoanableWorkRepository();
                    repository.save(work);

                    // Then, define a flash message to inform the user
                    this.addFlash(
                        request,
                        "success",
                        String.format(
                            "Vous avez crée une nouvelle oeuvre à prêter nommée <strong>%s %s</strong>.",
                            StringEscapeUtils.escapeHtml4(firstName),
                            StringEscapeUtils.escapeHtml4(lastName)
                        )
                    );
                    
                    // Finally, redirect the user
                    this.redirect("/loanableWorks.jsp?action=" + LoansController.ACTION_LIST, response);

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
            request.setAttribute("_last_first_name", firstName);
            request.setAttribute("_last_last_name", lastName);
            request.setAttribute("_last_town", town);
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
