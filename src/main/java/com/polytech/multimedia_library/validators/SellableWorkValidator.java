package com.polytech.multimedia_library.validators;

import com.polytech.multimedia_library.entities.Oeuvrevente;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class SellableWorkValidator implements Validator
{
    /**
     * 
     * @param type
     * @return 
     */
    @Override
    public boolean supports(Class<?> type)
    {
        return Oeuvrevente.class.equals(type);
    }

    /**
     * 
     * @param target
     * @param errors 
     */
    @Override
    public void validate(Object target, Errors errors)
    {
        if(target instanceof Oeuvrevente)
        {
            // Common checks
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvrevente", "NotEmpty.sellableWorkForm.titreOeuvrevente");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etatOeuvrevente", "NotEmpty.sellableWorkForm.etatOeuvrevente");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prixOeuvrevente", "NotEmpty.sellableWorkForm.prixOeuvrevente");
            
            // Specific checks
            Oeuvrevente work = (Oeuvrevente) target;
            
            if(work.getIdProprietaire() == null)
            {
                errors.rejectValue("idProprietaire", "NotEmpty.sellableWorkForm.idProprietaire");
            }
            
            if(work.getPrixOeuvrevente() < 0)
            {
                errors.rejectValue("prixOeuvrevente", "Invalid.sellableWorkForm.prixOeuvrevente");
            }
        }
    }
}
