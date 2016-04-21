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
     * 
     */
    @Autowired
    protected HttpServletRequest request;
    
    /**
     * 
     * @param viewName
     * @return 
     */
    protected ModelAndView render(String viewName)
    {
        return new ModelAndView(viewName);
    }
    
    /**
     * 
     * @param viewName
     * @param model
     * @return 
     */
    protected ModelAndView render(String viewName, ModelMap model)
    {
        return new ModelAndView(viewName, model);
    }
    
    /**
     * Redirects the user to the given URL.
     * 
     * @param url The URL to redirect to.
     * @return 
     */
    protected ModelAndView redirect(String url)
    {
        return new ModelAndView(new RedirectView(url, true));
    }
    
    /**
     * 
     * @param ex
     * @return 
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
     * 
     * @return 
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
     * 
     * @return 
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
     * 
     * @param type
     * @param contents 
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
