package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.flashes.Flash;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Bruno Buiret <bruno.buiret@etu.univ-lyon1.fr>
 */
public abstract class AbstractController extends HttpServlet
{
    /**
     * Forwards the response to the mentionned file.
     *
     * @param path The file's path.
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws javax.servlet.ServletException If a servlet-specific error occurs.
     * @throws java.io.IOException If an I/O error occurs.
     */
    protected void forward(String path, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
    
    /**
     * Displays an error page with the given <code>message</code>.
     * 
     * @param message The message to display.
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException  If an I/O error occurs.
     */
    protected void displayError(String message, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        request.setAttribute("_error_message", message);
        this.forward("/WEB-INF/error.jsp", request, response);
    }
    
    /**
     * Displays an error page with the given <code>message</code> 
     * 
     * @param message
     * @param exception
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void displayError(String message, Exception exception, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        request.setAttribute("_error_message", message);
        request.setAttribute("_error_exception", exception);
        this.forward("/WEB-INF/error.jsp", request, response);
    }
    
    /**
     * Gets the flash messages list from the session if it exists or
     * creates a new one.
     * 
     * @param session The session.
     * @return The flash list.
     */
    protected List<Flash> getFlashList(HttpSession session)
    {
        Object flashListItem = session.getAttribute("_flash");
        List<Flash> flashList = null;
        
        if(null != flashListItem && flashListItem instanceof List)
        {
            flashList = (List<Flash>) flashListItem;
        }
        else
        {
            flashList = new ArrayList<>();
        }
        
        return flashList;
    }
    
    /**
     * Alias to get the flash messages list.
     * 
     * @param request The servlet request.
     * @return The flash list.
     */
    protected List<Flash> getFlashList(HttpServletRequest request)
    {
        return this.getFlashList(request.getSession());
    }
    
    /**
     * Gets the flash messages list and clears it from session.
     * 
     * @param request The servlet request.
     * @return The flash list
     */
    protected List<Flash> getAndClearFlashList(HttpServletRequest request)
    {
        // Initialize vars
        HttpSession session = request.getSession();
        List<Flash> currentFlashList = this.getFlashList(session);
        List<Flash> flashList = new ArrayList<>(currentFlashList);
        
        // Erase the saved flash list
        currentFlashList.clear();
        session.setAttribute(
            "_flash",
            flashList
        );
        
        return flashList;
    }
    
    /**
     * Adds a flash message to the session.
     * 
     * @param request The servlet request.
     * @param type The flash message's type.
     * @param contents The flash message's contents.
     * @return This instance.
     */
    protected AbstractController addFlash(HttpServletRequest request, String type, String contents)
    {
        // Initialize vars
        HttpSession session = request.getSession();
        List<Flash> flashList = this.getFlashList(session);
        
        // Add the flash message
        flashList.add(new Flash(type, contents));
        
        // Then, erase the current flash list
        session.setAttribute(
            "_flash",
            flashList
        );
        
        return this;
    }
}
