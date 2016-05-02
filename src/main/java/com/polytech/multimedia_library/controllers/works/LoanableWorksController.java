package com.polytech.multimedia_library.controllers.works;

import com.polytech.multimedia_library.controllers.AbstractController;
import com.polytech.multimedia_library.editors.AdherentEditor;
import com.polytech.multimedia_library.editors.LoanableWorkEditor;
import com.polytech.multimedia_library.editors.OwnerEditor;
import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.Emprunt;
import com.polytech.multimedia_library.entities.Oeuvrepret;
import com.polytech.multimedia_library.entities.Proprietaire;
import com.polytech.multimedia_library.repositories.AdherentsRepository;
import com.polytech.multimedia_library.repositories.OwnersRepository;
import com.polytech.multimedia_library.repositories.works.LoanableWorksRepository;
import com.polytech.multimedia_library.utils.DateUtils;
import com.polytech.multimedia_library.validators.LoanValidator;
import com.polytech.multimedia_library.validators.LoanableWorkValidator;
import java.util.ArrayList;
import java.util.Date;
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
public class LoanableWorksController extends AbstractController
{
    /**
     * Initializes a binder with validators and editors to work
     * with sellable works.
     * 
     * @param binder The binder to initialize.
     */
    @InitBinder("workForm")
    protected void initWorkBinder(WebDataBinder binder)
    {
        binder.setValidator(new LoanableWorkValidator());
        binder.registerCustomEditor(Proprietaire.class, new OwnerEditor());
    }
    
    /**
     * Initializes a binder with validators and editors to work
     * with loans.
     * 
     * @param binder The binder to initialize.
     */
    @InitBinder("borrowingForm")
    protected void initBorrowingBinder(WebDataBinder binder)
    {
        binder.setValidator(new LoanValidator());
        binder.registerCustomEditor(Adherent.class, new AdherentEditor());
        binder.registerCustomEditor(Oeuvrepret.class, new LoanableWorkEditor());
    }
    
    /**
     * Displays a list loanable works.
     * 
     * @return The view to display.
     */
    @RequestMapping(value="/works/loanable", method=RequestMethod.GET)
    public ModelAndView list()
    {
        // Initialize vars
        LoanableWorksRepository repository = new LoanableWorksRepository();
        
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("works", repository.fetchAll());
        model.addAttribute("_flashList", this.getAndClearFlashList());
        
        return this.render("works/loans/list", model);
    }
    
    /**
     * Displays a form to add a loanable work.
     * 
     * @return The view to display.
     */
    @RequestMapping(value="/works/loanable/add", method=RequestMethod.GET)
    public ModelAndView add()
    {
        // Initialize vars
        OwnersRepository ownersRepository = new OwnersRepository();
        
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("_page_title", "Ajouter une nouvelle oeuvre à prêter");
        model.addAttribute("_page_current", "works_loans_add");
        model.addAttribute("workForm", new Oeuvrepret());
        model.addAttribute("ownersList", ownersRepository.fetchAll());
        
        return this.render("works/loans/form", model);
    }
    
    /**
     * Displays a form edit a loanable work.
     * 
     * @param workId The work's id.
     * @return The view to display.
     */
    @RequestMapping(value="/works/loanable/edit/{workId}")
    public ModelAndView edit(@PathVariable int workId)
    {
        // Initialize vars
        LoanableWorksRepository worksRepository = new LoanableWorksRepository();
        Oeuvrepret work = worksRepository.fetch(workId);
        
        if(work != null)
        {
            // Initialize additional vars
            OwnersRepository ownersRepository = new OwnersRepository();
            
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("_page_title", "Éditer une oeuvre à prêter");
            model.addAttribute("_page_current", "works_loans_edit");
            model.addAttribute("workForm", work);
            model.addAttribute("ownersList", ownersRepository.fetchAll());

            return this.render("works/loans/form", model);
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucune oeuvre à prêter ayant pour identifiant <strong>%d</strong>.",
                    workId
                )
            );
            
            return this.redirect("/works/loanable");
        }
    }
    
    /**
     * Handles the submission of a form to add or edit a loanable work.
     * 
     * @param work The work to save.
     * @param result The validation results.
     * @param isNew A boolean indicating if the work is new or not
     * @return 
     */
    @RequestMapping(value="/works/loanable/submit", method=RequestMethod.POST)
    public ModelAndView submit(
        @ModelAttribute("workForm") @Validated Oeuvrepret work,
        BindingResult result,
        @RequestParam(value="_is_new", required=false) boolean isNew
    )
    {
        if(!result.hasErrors())
        {
            // Save the work
            LoanableWorksRepository repository = new LoanableWorksRepository();
            repository.save(work);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "L'oeuvre à prêter nommée <strong>%s</strong> a été sauvegardée.",
                    StringEscapeUtils.escapeHtml(work.getTitreOeuvrepret())
                )
            );
            
            // Finally, redirect user
            return this.redirect("/works/loanable");
        }
        else
        {
            // Initialize vars
            OwnersRepository ownersRepository = new OwnersRepository();
            
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("workForm", work);
            model.addAttribute("ownersList", ownersRepository.fetchAll());
            
            if(isNew)
            {
                model.addAttribute("_page_title", "Ajouter une nouvelle oeuvre à prêter");
                model.addAttribute("_page_current", "works_loans_add");
            }
            else
            {
                model.addAttribute("_page_title", "Éditer une oeuvre à prêter");
                model.addAttribute("_page_current", "works_loans_edit");
            }

            return this.render("works/loans/form", model);
        }
    }
    
    /**
     * Handles the deletion of a single loanable work.
     * 
     * @param workId The work's id.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/works/loanable/delete/{workId}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int workId)
    {
        // Initialize vars
        LoanableWorksRepository repository = new LoanableWorksRepository();
        Oeuvrepret work = repository.fetch(workId);
        
        if(work != null)
        {
            // Delete the work
            repository.delete(work);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "L'oeuvre à prêter nommée <strong>%s</strong> a été supprimée.",
                    StringEscapeUtils.escapeHtml(work.getTitreOeuvrepret())
                )
            );
            
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucune oeuvre à prêter ayant pour identifiant <strong>%d</strong>.",
                    workId
                )
            );
        }
        
        // Finally, redirect user
        return this.redirect("/works/loanable");
    }
    
    /**
     * Handles the deletion of multiple loanable works.
     * 
     * @param ids The list of loanable works' ids.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/works/loanable/delete", method=RequestMethod.POST)
    public ModelAndView multiDelete(@RequestParam(value="ids[]", required=false) ArrayList<Integer> ids)
    {
        if(ids != null && ids.size() > 0)
        {
            // Initialize vars
            LoanableWorksRepository repository = new LoanableWorksRepository();
            List<Oeuvrepret> works = repository.fetch(ids);
            
            if(works.size() > 0)
            {
                // Delete the works
                repository.delete(works);
                
                // Then, register a flash message
                StringBuilder flashBuilder = new StringBuilder();
                
                if(works.size() > 1)
                {
                    flashBuilder.append("Les oeuvres à prêter suivantes ont été supprimées : ");
                    
                    for(int i = 0, j = works.size(); i < j; i++)
                    {
                        Oeuvrepret work = works.get(i);
                        
                        flashBuilder.append(
                            String.format(
                                "%s%s",
                                StringEscapeUtils.escapeHtml(work.getTitreOeuvrepret()),
                                i < j - 1 ? ", " : ""
                            )
                        );
                    }
                    
                    flashBuilder.append(".");
                }
                else
                {
                    Oeuvrepret work = works.get(0);
                    
                    flashBuilder.append(
                        String.format(
                            "L'oeuvre à prêter nommée <strong>%s</strong> a été supprimée.",
                            StringEscapeUtils.escapeHtml(work.getTitreOeuvrepret())
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
                    "Ces identifiants ne correspondent à aucune oeuvre à prêter."
                );
            }
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                "Vous n'avez sélectionné aucune oeuvre à prêter à supprimer."
            );
        }
        
        return this.redirect("/works/loanable");
    }
    
    /**
     * Displays a form to borrow a single loanable work.
     * 
     * @param workId The work's id.
     * @return The view to display.
     */
    @RequestMapping(value="/works/loanable/borrow/{workId}", method=RequestMethod.GET)
    public ModelAndView borrow(@PathVariable int workId)
    {
        // Initialize vars
        LoanableWorksRepository worksRepository = new LoanableWorksRepository();
        Oeuvrepret work = worksRepository.fetch(workId);
        
        if(work != null)
        {
            // Initialize additional vars
            Date today = DateUtils.getToday();
            AdherentsRepository adherentsRepository = new AdherentsRepository();
            Emprunt loan = new Emprunt();
            
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("borrowingForm", loan);
            model.addAttribute("adherentsList", adherentsRepository.fetchAll());
            model.addAttribute("today", today);

            return this.render("works/loans/borrow", model);
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucune oeuvre à prêter ayant pour identifiant <strong>%d</strong>.",
                    workId
                )
            );
            
            return this.redirect("/works/loanable");
        }
    }
    
    /**
     * Handles the submission of a form to borrow a loanable work.
     * 
     * @param booking The loan to save.
     * @param result The validation results.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/works/loanable/book", method=RequestMethod.POST)
    public ModelAndView handleBooking(
        @ModelAttribute("borrowingForm") @Validated Emprunt booking,
        BindingResult result
    )
    {
        return null;
    }
}
