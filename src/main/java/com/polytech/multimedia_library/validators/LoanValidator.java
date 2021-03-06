package com.polytech.multimedia_library.validators;

import com.polytech.multimedia_library.entities.Emprunt;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
public class LoanValidator implements Validator
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
        return Emprunt.class.equals(type);
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
        if(target instanceof Emprunt)
        {
            // Specific checks
            Emprunt loan = (Emprunt) target;

            if(loan.getAdherent() == null)
            {
                errors.rejectValue("adherent", "NotEmpty.borrowingForm.adherent");
            }

            if(loan.getOeuvrepret() == null)
            {
                errors.rejectValue("oeuvrepret", "NotEmpty.borrowingForm.oeuvrepret");
            }

            if(loan.getDateDebut() == null)
            {
                errors.rejectValue("dateDebut", "NotEmpty.borrowingForm.dateDebut");
            }

            if(loan.getDateFin() == null)
            {
                errors.rejectValue("dateFin", "NotEmpty.borrowingForm.dateFin");
            }

            if(
                loan.getDateDebut() != null
                && loan.getDateFin() != null
                && loan.getDateDebut().compareTo(loan.getDateFin()) > 0
            )
            {
                errors.rejectValue("dateFin", "Invalid.borrowingForm.dateFin");
            }
        }
    }
}
