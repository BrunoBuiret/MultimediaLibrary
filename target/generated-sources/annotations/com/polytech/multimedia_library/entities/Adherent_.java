package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.Emprunt;
import com.polytech.multimedia_library.entities.Reservation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-21T14:53:15")
@StaticMetamodel(Adherent.class)
public class Adherent_ { 

    public static volatile ListAttribute<Adherent, Reservation> reservationList;
    public static volatile ListAttribute<Adherent, Emprunt> empruntList;
    public static volatile SingularAttribute<Adherent, Integer> idAdherent;
    public static volatile SingularAttribute<Adherent, String> villeAdherent;
    public static volatile SingularAttribute<Adherent, String> nomAdherent;
    public static volatile SingularAttribute<Adherent, String> prenomAdherent;

}