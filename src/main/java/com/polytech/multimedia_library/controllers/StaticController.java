package com.polytech.multimedia_library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
@Controller
public class StaticController extends AbstractController
{
    /**
     * 
     * @return 
     */
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView home()
    {
        return new ModelAndView("static/home");
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(value="/contact", method=RequestMethod.GET)
    public ModelAndView contact()
    {
        return new ModelAndView("static/contact");
    }
}
