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
     * The action parameter's name.
     */
    protected final static String PARAMETER_ACTION = "action";
    
    /**
     * The error page's path.
     */
    protected static String ERROR_PAGE_PATH = "/WEB-INF/error.jsp";
    
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
     * Redirects the user to another URL.
     * 
     * @param url The URL to redirect to.
     * @param request The servlet request.
     * @param response The servlet response.
     * @return <code>null</code> so as to avoid to repeat <code>return null</code> after each call.
     * @throws IOException  If an I/O error occurs.
     */
    protected String redirect(String url, HttpServletRequest request, HttpServletResponse response)
    throws IOException
    {
        if(url.startsWith("/"))
        {
            url = url.substring(1);
        }
        
        response.sendRedirect(request.getContextPath() + "/" + response.encodeRedirectURL(url));
        
        return null;
    }
    
    /**
     * Displays an error page with the given message.
     * 
     * @param message The message to display.
     * @param request The servlet request.
     * @return The error page's path.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected String displayError(String message, HttpServletRequest request)
    throws ServletException, IOException
    {
        request.setAttribute("_error_message", message);
        
        return AbstractController.ERROR_PAGE_PATH;
    }
    
    /**
     * Displays an error page with the given title and message.
     * 
     * @param title The title to display.
     * @param message The message to display.
     * @param request The servlet request.
     * @return The error page's path.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     * @see #displayError(java.lang.String, javax.servlet.http.HttpServletRequest)
     */
    protected String displayError(String title, String message, HttpServletRequest request)
    throws ServletException, IOException
    {
        request.setAttribute("_error_title", title);
        
        return this.displayError(message, request);
    }
    
    /**
     * Displays an error page for the given exception with the specified message.
     * 
     * @param message The message to display.
     * @param exception The exception to handle.
     * @param request The servlet request.
     * @return The error page's path.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     * @see #displayError(java.lang.String, javax.servlet.http.HttpServletRequest) 
     */
    protected String displayError(String message, Exception exception, HttpServletRequest request)
    throws ServletException, IOException
    {
        request.setAttribute("_error_exception", exception);
        
        return this.displayError(message, request);
    }
    
    /**
     * Displays an error page for the given exception with the specified
     * title and message.
     * 
     * @param title The title to display.
     * @param message The message to display.
     * @param exception The exception to handle.
     * @param request The servlet request.
     * @return The error page's path.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     * @see #displayError(java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest) 
     */
    protected String displayError(String title, String message, Exception exception, HttpServletRequest request)
    throws ServletException, IOException
    {
        request.setAttribute("_error_exception", exception);
        
        return this.displayError(title, message, request);
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
        List<Flash> flashList;
        
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
            currentFlashList
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
