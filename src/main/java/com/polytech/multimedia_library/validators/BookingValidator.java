package com.polytech.multimedia_library.validators;

import com.polytech.multimedia_library.entities.Reservation;
import org.springframework.validation.Errors;
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
            // Specific checks
            Reservation booking = (Reservation) target;

            if(booking.getAdherent() == null)
            {
                errors.rejectValue("adherent", "NotEmpty.bookingForm.adherent");
            }

            if(booking.getOeuvrevente() == null)
            {
                errors.rejectValue("dateReservation", "NotEmpty.bookingForm.oeuvrevente");
            }

            if(booking.getDateReservation() == null)
            {
                errors.rejectValue("dateReservation", "NotEmpty.bookingForm.dateReservation");
            }

            // @todo Check status
        }
    }
}
