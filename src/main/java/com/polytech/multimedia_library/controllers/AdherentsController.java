package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.repositories.AdherentsRepository;
import com.polytech.multimedia_library.validators.AdherentValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
@Controller
public class AdherentsController extends AbstractController
{
    /**
     * Initializes a binder with validators and editors.
     * 
     * @param binder The binder to initialize.
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new AdherentValidator());
    }
    
    /**
     * Displays a list of adherents.
     * 
     * @return The view to display.
     */
    @RequestMapping(value="/adherents", method=RequestMethod.GET)
    public ModelAndView list()
    {
        // Initialize vars
        AdherentsRepository repository = new AdherentsRepository();
        
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("adherents", repository.fetchAll());
        model.addAttribute("_flashList", this.getAndClearFlashList());
        
        return this.render("adherents/list", model);
    }
    
    /**
     * Displays a form to add an adherent.
     * 
     * @return The view to display.
     */
    @RequestMapping(value="/adherents/add", method=RequestMethod.GET)
    public ModelAndView add()
    {
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("_page_title", "Ajouter un nouvel adhérent");
        model.addAttribute("_page_current", "adherents_add");
        model.addAttribute("adherentForm", new Adherent());
        
        return this.render("adherents/form", model);
    }
    
    /**
     * Displays a form to edit an adherent.
     * 
     * @param adherentId The adherent's id.
     * @return The view to display.
     */
    @RequestMapping(value="/adherents/edit/{adherentId}")
    public ModelAndView edit(@PathVariable int adherentId)
    {
        // Initialize vars
        AdherentsRepository repository = new AdherentsRepository();
        Adherent adherent = repository.fetch(adherentId);
        
        if(adherent != null)
        {
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("_page_title", "Éditer un adhérent");
            model.addAttribute("_page_current", "adherents_edit");
            model.addAttribute("adherentForm", adherent);

            return this.render("adherents/form", model);
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucun adhérent ayant pour identifiant <strong>%d</strong>.",
                    adherentId
                )
            );
            
            return this.redirect("/adherents");
        }
    }
    
    /**
     * Handles the submission of a form to add or edit an adherent.
     * 
     * @param adherent The adherent to save.
     * @param result The validation results.
     * @param isNew A boolean indicating if the adherent is new or not.
     * @return The view to display or to use to redirect.
     */
    @RequestMapping(value="/adherents/submit", method=RequestMethod.POST)
    public ModelAndView submit(
        @ModelAttribute("adherentForm") @Validated Adherent adherent,
        BindingResult result,
        @RequestParam(value="_is_new", required=false) boolean isNew
    )
    {
        if(!result.hasErrors())
        {
            // Save the adherent
            AdherentsRepository repository = new AdherentsRepository();
            repository.save(adherent);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "L'adhérent nommé <strong>%s %s</strong> a été sauvegardé.",
                    StringEscapeUtils.escapeHtml(adherent.getPrenomAdherent()),
                    StringEscapeUtils.escapeHtml(adherent.getNomAdherent())
                )
            );
            
            // Finally, redirect user
            return this.redirect("/adherents");
        }
        else
        {
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("adherentForm", adherent);
            
            if(isNew)
            {
                model.addAttribute("_page_title", "Ajouter un nouveau propriétaire");
                model.addAttribute("_page_current", "owners_add");
            }
            else
            {
                model.addAttribute("_page_title", "Éditer un propriétaire");
                model.addAttribute("_page_current", "owners_edit");
            }

            return this.render("adherents/form", model);
        }
    }
    
    /**
     * Handles the deletion of a single adherent.
     * 
     * @param adherentId The adherent's id.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/adherents/delete/{adherentId}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int adherentId)
    {
        // Initialize vars
        AdherentsRepository repository = new AdherentsRepository();
        Adherent adherent = repository.fetch(adherentId);
        
        if(adherent != null)
        {
            // Delete the adherent
            repository.delete(adherent);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "L'adhérent nommé <strong>%s %s</strong> a été supprimé.",
                    StringEscapeUtils.escapeHtml(adherent.getPrenomAdherent()),
                    StringEscapeUtils.escapeHtml(adherent.getNomAdherent())
                )
            );
            
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucun adhérent ayant pour identifiant <strong>%d</strong>.",
                    adherentId
                )
            );
        }
        
        // Finally, redirect user
        return this.redirect("/adherents");
    }
    
    /**
     * Handles the deletion of multiple adherents.
     * 
     * @param ids The list of adherents' ids.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/adherents/delete", method=RequestMethod.POST)
    public ModelAndView multiDelete(@RequestParam(value="ids[]", required=false) ArrayList<Integer> ids)
    {
        if(ids != null && ids.size() > 0)
        {
            // Initialize vars
            AdherentsRepository repository = new AdherentsRepository();
            List<Adherent> adherents = repository.fetch(ids);
            
            if(adherents.size() > 0)
            {
                // Delete the adherents
                repository.delete(adherents);
                
                // Then, register a flash message
                StringBuilder flashBuilder = new StringBuilder();
                
                if(adherents.size() > 1)
                {
                    flashBuilder.append("Les adhérents suivants ont été supprimés : ");
                    
                    for(int i = 0, j = adherents.size(); i < j; i++)
                    {
                        Adherent adherent = adherents.get(i);
                        
                        flashBuilder.append(
                            String.format(
                                "%s %s%s",
                                StringEscapeUtils.escapeHtml(adherent.getPrenomAdherent()),
                                StringEscapeUtils.escapeHtml(adherent.getNomAdherent()),
                                i < j - 1 ? ", " : ""
                            )
                        );
                    }
                    
                    flashBuilder.append(".");
                }
                else
                {
                    Adherent adherent = adherents.get(0);
                    
                    flashBuilder.append(
                        String.format(
                            "L'adhérent nommé <strong>%s %s</strong> a été supprimé.",
                            StringEscapeUtils.escapeHtml(adherent.getPrenomAdherent()),
                            StringEscapeUtils.escapeHtml(adherent.getNomAdherent())
                        )
                    );
                }
                
                this.addFlash(
                    "success",
                    flashBuilder.toString()
                );
            }
            else
            {
                // Register a flash message
                this.addFlash(
                    "danger",
                    "Ces identifiants ne correspondent à aucun adhérent."
                );
            }
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                "Vous n'avez sélectionné aucun adhérent à supprimer."
            );
        }
        
        return this.redirect("/adherents");
    }
}
