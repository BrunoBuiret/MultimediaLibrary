package com.polytech.multimedia_library.validators;

import com.polytech.multimedia_library.entities.Proprietaire;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class OwnerValidator implements Validator
{
    /**
     * 
     * @param type
     * @return 
     */
    @Override
    public boolean supports(Class<?> type)
    {
        return Proprietaire.class.equals(type);
    }

    /**
     * 
     * @param target
     * @param errors 
     */
    @Override
    public void validate(Object target, Errors errors)
    {
        if(target instanceof Proprietaire)
        {
            // Common checks
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenomProprietaire", "NotEmpty.ownerForm.prenomProprietaire");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomProprietaire", "NotEmpty.ownerForm.nomProprietaire");
        }
    }
}
