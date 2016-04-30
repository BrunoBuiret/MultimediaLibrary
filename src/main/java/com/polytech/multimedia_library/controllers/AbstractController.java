package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.AbstractException;
import com.polytech.multimedia_library.session.Flash;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public abstract class AbstractController
{
    /**
     * The request sent to the server.
     */
    @Autowired
    protected HttpServletRequest request;
    
    /**
     * Renders a view whose name is <code>viewName</code> without any
     * model.
     * 
     * @param viewName The view's name.
     * @return The view only.
     */
    protected ModelAndView render(String viewName)
    {
        return new ModelAndView(viewName);
    }
    
    /**
     * Renders a view whose name is <code>viewName</code> with an accompanying
     * model.
     * 
     * @param viewName The view's name.
     * @param model The accompanying model.
     * @return The view with its model.
     */
    protected ModelAndView render(String viewName, ModelMap model)
    {
        return new ModelAndView(viewName, model);
    }
    
    /**
     * Redirects the user to the given URL.
     * 
     * @param url The URL to redirect to.
     * @return A redirection view.
     */
    protected ModelAndView redirect(String url)
    {
        return new ModelAndView(new RedirectView(url, true));
    }
    
    /**
     * Handles exceptions that haven't been caught yet.
     * 
     * @param ex The exception that occured.
     * @return A view to display informations about the exception.
     */
    @ExceptionHandler(AbstractException.class)
    public ModelAndView exceptionHandler(AbstractException ex)
    {
        ModelAndView modelAndView = new ModelAndView("error");
        
        modelAndView.addObject("customTitle", ex.getTitle());
        modelAndView.addObject("customMessage", ex.getMessage());
        modelAndView.addObject("errorMessage", ex.getCause().getMessage());
        modelAndView.addObject("stackTrace", ex.getCause().getStackTrace());
        
        return modelAndView;
    }
    
    /**
     * Creates the flash list if it doesn't exist yet and then returns it.
     * 
     * @return The flash list.
     */
    protected List<Flash> getFlashList()
    {
        // Initialize vars
        Object flashListObject = this.request.getSession().getAttribute("_flashes");
        List<Flash> flashList;
        
        if(flashListObject != null && flashListObject instanceof List)
        {
            flashList = (List<Flash>) flashListObject;
        }
        else
        {
            flashList = new ArrayList<>();
        }
        
        return flashList;
    }
    
    /**
     * Creates a copy of the current flash list before clearing it, then returns
     * the copy.
     * 
     * @return The flash list.
     */
    protected List<Flash> getAndClearFlashList()
    {
        // Initialize vars
        HttpSession session = this.request.getSession();
        List<Flash> currentFlashList = this.getFlashList();
        List<Flash> flashList = new ArrayList<>(currentFlashList);
        
        // Erase the saved flash list
        currentFlashList.clear();
        session.setAttribute(
            "_flashes",
            currentFlashList
        );
        
        return flashList;
    }
    
    /**
     * Adds a flash message to the flash list.
     * 
     * @param type The flash message's type.
     * @param contents The flash message's contents.
     */
    protected void addFlash(String type, String contents)
    {
        // Initialize vars
        HttpSession session = this.request.getSession();
        List<Flash> flashList = this.getFlashList();
        
        // Add the flash message and memorize the new list
        flashList.add(new Flash(type, contents));
        session.setAttribute("_flashes", flashList);
    }
}
