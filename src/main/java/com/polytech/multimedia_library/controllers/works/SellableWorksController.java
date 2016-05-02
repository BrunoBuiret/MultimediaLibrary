package com.polytech.multimedia_library.controllers.works;

import com.polytech.multimedia_library.controllers.AbstractController;
import com.polytech.multimedia_library.editors.AdherentEditor;
import com.polytech.multimedia_library.editors.OwnerEditor;
import com.polytech.multimedia_library.editors.SellableWorkEditor;
import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.Oeuvrevente;
import com.polytech.multimedia_library.entities.Proprietaire;
import com.polytech.multimedia_library.entities.Reservation;
import com.polytech.multimedia_library.repositories.AdherentsRepository;
import com.polytech.multimedia_library.repositories.OwnersRepository;
import com.polytech.multimedia_library.repositories.works.SellableWorksRepository;
import com.polytech.multimedia_library.utils.DateUtils;
import com.polytech.multimedia_library.validators.BookingValidator;
import com.polytech.multimedia_library.validators.SellableWorkValidator;
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
public class SellableWorksController extends AbstractController
{
    /**
     * Initializes a binder with validators and editors to work
     * with sellable works.
     * 
     * @param binder The binder to initialize.
     * @see http://stackoverflow.com/questions/25873363/configure-two-initbinder-to-work-with-the-same-model-or-entity-but-for-differen
     */
    @InitBinder("workForm")
    protected void initWorkBinder(WebDataBinder binder)
    {
        binder.setValidator(new SellableWorkValidator());
        binder.registerCustomEditor(Proprietaire.class, new OwnerEditor());
    }
    
    /**
     * Initializes a binder with validators and editors to work
     * with bookings.
     * 
     * @param binder The binder to initialize.
     */
    @InitBinder("bookingForm")
    protected void initBookingBinder(WebDataBinder binder)
    {
        binder.setValidator(new BookingValidator());
        binder.registerCustomEditor(Adherent.class, new AdherentEditor());
        binder.registerCustomEditor(Oeuvrevente.class, new SellableWorkEditor());
    }
    
    /**
     * Displays a list of sellable works.
     * 
     * @return The view to display.
     */
    @RequestMapping(value="/works/sellable", method=RequestMethod.GET)
    public ModelAndView list()
    {
        // Initialize vars
        SellableWorksRepository repository = new SellableWorksRepository();
        
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("works", repository.fetchAll());
        model.addAttribute("_flashList", this.getAndClearFlashList());
        
        return this.render("works/sales/list", model);
    }
    
    /**
     * Displays a form to add a sellable work.
     * 
     * @return The view to display.
     */
    @RequestMapping(value="/works/sellable/add", method=RequestMethod.GET)
    public ModelAndView add()
    {
        // Initialize vars
        OwnersRepository ownersRepository = new OwnersRepository();
        
        // Populate model
        ModelMap model = new ModelMap();
        model.addAttribute("_page_title", "Ajouter une nouvelle oeuvre à vendre");
        model.addAttribute("_page_current", "works_sales_add");
        model.addAttribute("workForm", new Oeuvrevente());
        model.addAttribute("ownersList", ownersRepository.fetchAll());
        
        return this.render("works/sales/form", model);
    }
    
    /**
     * Displays a form edit a sellable work.
     * 
     * @param workId The work's id.
     * @return The view to display.
     */
    @RequestMapping(value="/works/sellable/edit/{workId}")
    public ModelAndView edit(@PathVariable int workId)
    {
        // Initialize vars
        SellableWorksRepository worksRepository = new SellableWorksRepository();
        Oeuvrevente work = worksRepository.fetch(workId);
        
        if(work != null)
        {
            // Initialize additional vars
            OwnersRepository ownersRepository = new OwnersRepository();
            
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("_page_title", "Éditer une oeuvre à vendre");
            model.addAttribute("_page_current", "works_sales_edit");
            model.addAttribute("workForm", work);
            model.addAttribute("ownersList", ownersRepository.fetchAll());

            return this.render("works/sales/form", model);
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucune oeuvre à vendre ayant pour identifiant <strong>%d</strong>.",
                    workId
                )
            );
            
            return this.redirect("/works/sellable");
        }
    }
    
    /**
     * Handles the submission of a form to add or edit a sellable work.
     * 
     * @param work The work to save.
     * @param result The validation results.
     * @param isNew A boolean indicating if the work is new or not.
     * @return The view to display or to use to redirect.
     */
    @RequestMapping(value="/works/sellable/submit", method=RequestMethod.POST)
    public ModelAndView submit(
        @ModelAttribute("workForm") @Validated Oeuvrevente work,
        BindingResult result,
        @RequestParam(value="_is_new", required=false) boolean isNew
    )
    {
        if(!result.hasErrors())
        {
            // Save the work
            SellableWorksRepository repository = new SellableWorksRepository();
            repository.save(work);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "L'oeuvre à vendre nommée <strong>%s</strong> a été sauvegardée.",
                    StringEscapeUtils.escapeHtml(work.getTitreOeuvrevente())
                )
            );
            
            // Finally, redirect user
            return this.redirect("/works/sellable");
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
                model.addAttribute("_page_title", "Ajouter une nouvelle oeuvre à vendre");
                model.addAttribute("_page_current", "works_sales_add");
            }
            else
            {
                model.addAttribute("_page_title", "Éditer une oeuvre à vendre");
                model.addAttribute("_page_current", "works_sales_edit");
            }

            return this.render("works/sales/form", model);
        }
    }
    
    /**
     * Handles the deletion of a single sellable work.
     * 
     * @param workId The work's id.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/works/sellable/delete/{workId}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int workId)
    {
        // Initialize vars
        SellableWorksRepository repository = new SellableWorksRepository();
        Oeuvrevente work = repository.fetch(workId);
        
        if(work != null)
        {
            // Delete the work
            repository.delete(work);
            
            // Then, register a flash message
            this.addFlash(
                "success", 
                String.format(
                    "L'oeuvre à vendre nommée <strong>%s</strong> a été supprimée.",
                    StringEscapeUtils.escapeHtml(work.getTitreOeuvrevente())
                )
            );
            
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucune oeuvre à vendre ayant pour identifiant <strong>%d</strong>.",
                    workId
                )
            );
        }
        
        // Finally, redirect user
        return this.redirect("/works/sellable");
    }
    
    /**
     * Handles the deletion of multiple sellable works.
     * 
     * @param ids The list of works' ids.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/works/sellable/delete", method=RequestMethod.POST)
    public ModelAndView multiDelete(@RequestParam(value="ids[]", required=false) ArrayList<Integer> ids)
    {
        if(ids != null && ids.size() > 0)
        {
            // Initialize vars
            SellableWorksRepository repository = new SellableWorksRepository();
            List<Oeuvrevente> works = repository.fetch(ids);
            
            if(works.size() > 0)
            {
                // Delete the works
                repository.delete(works);
                
                // Then, register a flash message
                StringBuilder flashBuilder = new StringBuilder();
                
                if(works.size() > 1)
                {
                    flashBuilder.append("Les oeuvres à vendre suivantes ont été supprimées : ");
                    
                    for(int i = 0, j = works.size(); i < j; i++)
                    {
                        Oeuvrevente work = works.get(i);
                        
                        flashBuilder.append(
                            String.format(
                                "%s%s",
                                StringEscapeUtils.escapeHtml(work.getTitreOeuvrevente()),
                                i < j - 1 ? ", " : ""
                            )
                        );
                    }
                    
                    flashBuilder.append(".");
                }
                else
                {
                    Oeuvrevente work = works.get(0);
                    
                    flashBuilder.append(
                        String.format(
                            "L'oeuvre à vendre nommée <strong>%s</strong> a été supprimée.",
                            StringEscapeUtils.escapeHtml(work.getTitreOeuvrevente())
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
                    "Ces identifiants ne correspondent à aucune oeuvre à vendre."
                );
            }
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                "Vous n'avez sélectionné aucune oeuvre à vendre à supprimer."
            );
        }
        
        return this.redirect("/works/sellable");
    }
    
    /**
     * Displays a form to book a single sellable work.
     * 
     * @param workId The work's id.
     * @return The view to display.
     */
    @RequestMapping(value="/works/sellable/book/{workId}", method=RequestMethod.GET)
    public ModelAndView book(@PathVariable int workId)
    {
        // Initialize vars
        SellableWorksRepository worksRepository = new SellableWorksRepository();
        Oeuvrevente work = worksRepository.fetch(workId);
        
        if(work != null)
        {
            // Initialize additional vars
            Date today = DateUtils.getToday();
            AdherentsRepository adherentsRepository = new AdherentsRepository();
            Reservation booking = new Reservation();
            booking.setDateReservation(today);
            booking.setOeuvrevente(work);
            booking.setStatut("?");
            
            // Populate model
            ModelMap model = new ModelMap();
            model.addAttribute("bookingForm", booking);
            model.addAttribute("adherentsList", adherentsRepository.fetchAll());
            model.addAttribute("today", today);

            return this.render("works/sales/book", model);
        }
        else
        {
            // Register a flash message
            this.addFlash(
                "danger", 
                String.format(
                    "Il n'existe aucune oeuvre à vendre ayant pour identifiant <strong>%d</strong>.",
                    workId
                )
            );
            
            return this.redirect("/works/sellable");
        }
    }
    
    /**
     * Handles the submission of a form to book a sellable work.
     * 
     * @param booking The booking to save.
     * @param result The validation results.
     * @return The view to use to redirect.
     */
    @RequestMapping(value="/works/sellable/book", method=RequestMethod.POST)
    public ModelAndView handleBooking(
        @ModelAttribute("bookingForm") @Validated Reservation booking,
        BindingResult result
    )
    {
        System.out.println("pk: " + booking.getReservationPK());
        System.out.println("ad: " + booking.getAdherent());
        System.out.println("dr: " + booking.getDateReservation());
        System.out.println("ov: " + booking.getOeuvrevente());
        System.out.println("st: " + booking.getStatut());
             
        if(!result.hasErrors())
        {
            // Save the booking
            SellableWorksRepository repository = new SellableWorksRepository();
            
            booking.getOeuvrevente().getReservationList().add(booking);
            repository.save(booking.getOeuvrevente());
            
            // Then, register a flash message
            this.addFlash(
                "success",
                String.format(
                    "L'oeuvre à vendre nommée <strong>%s</strong> a été réservé à " +
                    "la vente pour <strong>%s %s</strong>.",
                    StringEscapeUtils.escapeHtml(booking.getOeuvrevente().getTitreOeuvrevente()),
                    StringEscapeUtils.escapeHtml(booking.getAdherent().getPrenomAdherent()),
                    StringEscapeUtils.escapeHtml(booking.getAdherent().getNomAdherent())
                )
            );
            
            // Finally, redirect user
            return this.redirect("/works/sellable");
        }
        else
        {
            // Initialize vars
             AdherentsRepository adherentsRepository = new AdherentsRepository();
             
             // Populate model
             ModelMap model = new ModelMap();
             model.addAttribute("bookingForm", booking);
             model.addAttribute("adherentsList", adherentsRepository.fetchAll());
             model.addAttribute("today", DateUtils.getToday());
             
             return this.render("works/sales/book", model);
        }
    }
}
