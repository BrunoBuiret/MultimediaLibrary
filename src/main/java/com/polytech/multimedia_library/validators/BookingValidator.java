package com.polytech.multimedia_library.validators;

import com.polytech.multimedia_library.entities.Reservation;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class BookingValidator implements Validator
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
        return Reservation.class.equals(type);
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
        if(target instanceof Reservation)
        {
            // Common checks
            /*
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenomAdherent", "NotEmpty.adherentForm.prenomAdherent");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomAdherent", "NotEmpty.adherentForm.nomAdherent");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "villeAdherent", "NotEmpty.adherentForm.villeAdherent");
            */
        }
    }
}
