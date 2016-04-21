package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.Adherent;
import com.polytech.multimedia_library.entities.Oeuvrepret;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-21T14:53:15")
@StaticMetamodel(Emprunt.class)
public class Emprunt_ { 

    public static volatile SingularAttribute<Emprunt, Integer> idEmprunt;
    public static volatile SingularAttribute<Emprunt, Date> dateDebut;
    public static volatile SingularAttribute<Emprunt, Adherent> idAdherent;
    public static volatile SingularAttribute<Emprunt, Oeuvrepret> idOeuvrepret;
    public static volatile SingularAttribute<Emprunt, Date> dateFin;

}