package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.Emprunt;
import com.polytech.multimedia_library.entities.Proprietaire;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-21T14:53:15")
@StaticMetamodel(Oeuvrepret.class)
public class Oeuvrepret_ { 

    public static volatile ListAttribute<Oeuvrepret, Emprunt> empruntList;
    public static volatile SingularAttribute<Oeuvrepret, Integer> idOeuvrepret;
    public static volatile SingularAttribute<Oeuvrepret, Proprietaire> idProprietaire;
    public static volatile SingularAttribute<Oeuvrepret, String> titreOeuvrepret;

}