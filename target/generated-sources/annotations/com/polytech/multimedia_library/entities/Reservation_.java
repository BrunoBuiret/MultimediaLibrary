package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.Oeuvrevente;
import com.polytech.multimedia_library.entities.ReservationPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-21T14:53:15")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

    public static volatile SingularAttribute<Reservation, ReservationPK> reservationPK;
    public static volatile SingularAttribute<Reservation, Oeuvrevente> oeuvrevente;
    public static volatile SingularAttribute<Reservation, Adherent> adherent;
    public static volatile SingularAttribute<Reservation, Date> dateReservation;
    public static volatile SingularAttribute<Reservation, String> statut;

}