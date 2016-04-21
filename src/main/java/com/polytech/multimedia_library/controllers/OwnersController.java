package com.polytech.multimedia_library.controllers;

import com.polytech.multimedia_library.entities.Proprietaire;
import com.polytech.multimedia_library.repositories.OwnerRepository;
import com.polytech.multimedia_library.validators.AdherentValidator;
import com.polytech.multimedia_library.validators.OwnerValidator;
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
public class OwnersController extends AbstractController
{
    /**
     * 
     * @param binder 
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new OwnerValidator());
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(value="/owners", method=RequestMethod.GET)
    public ModelAndView list()
    {
        // Initialize vars
        OwnerRepository repository = new OwnerRepository();
        
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("owners", repository.fetchAll());
        model.addAttribute("_flashList", this.getAndClearFlashList());
        
        return this.render("owners/list", model);
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(value="/owners/add")
    public ModelAndView add()
    {
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("_page_title", "Ajouter un nouveau propriétaire");
        model.addAttribute("_page_current", "owners_add");
        model.addAttribute("ownerForm", new Proprietaire());
        
        return this.render("owners/form", model);
    }
    
    /**
     * 
     * @param ownerId
     * @return 
     */
    @RequestMapping(value="/owners/edit/{ownerId}")
    public ModelAndView edit(@PathVariable int ownerId)
    {
        // Initialize vars
        OwnerRepository repository = new OwnerRepository();
        Proprietaire owner = repository.fetch(ownerId);
        
        if(owner != null)
        {
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("_page_title", "Éditer un propriétaire");
            model.addAttribute("_page_current", "owners_edit");
            model.addAttribute("ownerForm", owner);

            return this.render("owners/form", model);
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucun propriétaire ayant pour identifiant <strong>%d</strong>.",
                    ownerId
                )
            );
            
            return this.redirect("/owners");
        }
    }
    
    /**
     * 
     * @param owner
     * @param result
     * @param isNew
     * @return 
     */
    @RequestMapping(value="/owners/submit", method=RequestMethod.POST)
    public ModelAndView submit(
        @ModelAttribute("ownerForm") @Validated Proprietaire owner,
        BindingResult result,
        @RequestParam(value="_is_new", required=false) boolean isNew
    )
    {
        if(!result.hasErrors())
        {
            // Save the owner
            OwnerRepository repository = new OwnerRepository();
            repository.save(owner);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "Le propriétaire nommé <strong>%s %s</strong> a été sauvegardé.",
                    StringEscapeUtils.escapeHtml(owner.getPrenomProprietaire()),
                    StringEscapeUtils.escapeHtml(owner.getNomProprietaire())
                )
            );
            
            // Finally, redirect user
            return this.redirect("/owners");
        }
        else
        {
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("ownerForm", owner);
            
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

            return this.render("owners/form", model);
        }
    }
    
    /**
     * 
     * @param ownerId
     * @return 
     */
    @RequestMapping(value="/owners/delete/{ownerId}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int ownerId)
    {
        // Initialize vars
        OwnerRepository repository = new OwnerRepository();
        Proprietaire owner = repository.fetch(ownerId);
        
        if(owner != null)
        {
            // Delete the owner
            repository.delete(owner);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "Le propriétaire nommé <strong>%s %s</strong> a été supprimé.",
                    StringEscapeUtils.escapeHtml(owner.getPrenomProprietaire()),
                    StringEscapeUtils.escapeHtml(owner.getNomProprietaire())
                )
            );
            
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucun propriétaire ayant pour identifiant <strong>%d</strong>.",
                    ownerId
                )
            );
        }
        
        // Finally, redirect user
        return this.redirect("/owners");
    }
    
    /**
     * 
     * @param ids
     * @return 
     */
    @RequestMapping(value="/owners/delete", method=RequestMethod.POST)
    public ModelAndView multiDelete(@RequestParam(value="ids[]", required=false) ArrayList<Integer> ids)
    {
        if(ids != null && ids.size() > 0)
        {
            // Initialize vars
            OwnerRepository repository = new OwnerRepository();
            List<Proprietaire> owners = repository.fetch(ids);
            
            if(owners.size() > 0)
            {
                // Delete the owners
                repository.delete(owners);
                
                // Then, register a flash message
                StringBuilder flashBuilder = new StringBuilder();
                
                if(owners.size() > 1)
                {
                    flashBuilder.append("Les propriétaires suivants ont été supprimés : ");
                    
                    for(int i = 0, j = owners.size(); i < j; i++)
                    {
                        Proprietaire owner = owners.get(i);
                        
                        flashBuilder.append(
                            String.format(
                                "%s %s%s",
                                StringEscapeUtils.escapeHtml(owner.getPrenomProprietaire()),
                                StringEscapeUtils.escapeHtml(owner.getNomProprietaire()),
                                i < j - 1 ? ", " : ""
                            )
                        );
                    }
                    
                    flashBuilder.append(".");
                }
                else
                {
                    Proprietaire owner = owners.get(0);
                    
                    flashBuilder.append(
                        String.format(
                            "Le propriétaire nommé <strong>%s %s</strong> a été supprimé.",
                            StringEscapeUtils.escapeHtml(owner.getPrenomProprietaire()),
                            StringEscapeUtils.escapeHtml(owner.getNomProprietaire())
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
                    "Ces identifiants ne correspondent à aucun propriétaire."
                );
            }
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                "Vous n'avez sélectionné aucun propriétaire à supprimer."
            );
        }
        
        return this.redirect("/owners");
    }
}
