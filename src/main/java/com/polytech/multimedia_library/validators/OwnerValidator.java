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
     * Tests if this validator can be used for this class.
     *
     * @param type The class to test.
     * @return <code>true</code> if the validator supports this class,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean supports(Class<?> type)
    {
        return Proprietaire.class.equals(type);
    }

    /**
     * Validates an object.
     *
     * @param target The object to validate.
     * @param errors The errors list.
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
