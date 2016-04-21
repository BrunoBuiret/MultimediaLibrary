package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.Proprietaire;
import com.polytech.multimedia_library.entities.Reservation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-21T14:53:15")
@StaticMetamodel(Oeuvrevente.class)
public class Oeuvrevente_ { 

    public static volatile ListAttribute<Oeuvrevente, Reservation> reservationList;
    public static volatile SingularAttribute<Oeuvrevente, Float> prixOeuvrevente;
    public static volatile SingularAttribute<Oeuvrevente, Integer> idOeuvrevente;
    public static volatile SingularAttribute<Oeuvrevente, String> etatOeuvrevente;
    public static volatile SingularAttribute<Oeuvrevente, String> titreOeuvrevente;
    public static volatile SingularAttribute<Oeuvrevente, Proprietaire> idProprietaire;

}