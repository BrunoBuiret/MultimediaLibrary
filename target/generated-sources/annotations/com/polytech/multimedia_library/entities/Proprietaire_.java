package com.polytech.multimedia_library.entities;

import com.polytech.multimedia_library.entities.Oeuvrepret;
import com.polytech.multimedia_library.entities.Oeuvrevente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-21T14:53:15")
@StaticMetamodel(Proprietaire.class)
public class Proprietaire_ { 

    public static volatile SingularAttribute<Proprietaire, Integer> idProprietaire;
    public static volatile ListAttribute<Proprietaire, Oeuvrevente> oeuvreventeList;
    public static volatile ListAttribute<Proprietaire, Oeuvrepret> oeuvrepretList;
    public static volatile SingularAttribute<Proprietaire, String> nomProprietaire;
    public static volatile SingularAttribute<Proprietaire, String> prenomProprietaire;

}