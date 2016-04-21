package com.polytech.multimedia_library.validators;

import com.polytech.multimedia_library.entities.Oeuvrepret;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class LoanableWorkValidator implements Validator
{
    /**
     * 
     * @param type
     * @return 
     */
    @Override
    public boolean supports(Class<?> type)
    {
        return Oeuvrepret.class.equals(type);
    }

    /**
     * 
     * @param target
     * @param errors 
     */
    @Override
    public void validate(Object target, Errors errors)
    {
        if(target instanceof Oeuvrepret)
        {
            // Common checks
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvrepret", "NotEmpty.loanableWorkForm.titreOeuvrepret");
            
            // Specific checks
            Oeuvrepret work = (Oeuvrepret) target;
            
            if(work.getIdProprietaire() == null)
            {
                errors.rejectValue("idProprietaire", "NotEmpty.loanableWorkForm.idProprietaire");
            }
        }
    }
}
