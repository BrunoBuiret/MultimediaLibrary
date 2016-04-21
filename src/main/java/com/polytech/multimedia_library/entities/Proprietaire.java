/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polytech.multimedia_library.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "proprietaire")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Proprietaire.findAll", query = "SELECT p FROM Proprietaire p"),
    @NamedQuery(name = "Proprietaire.findByIdProprietaire", query = "SELECT p FROM Proprietaire p WHERE p.idProprietaire = :idProprietaire"),
    @NamedQuery(name = "Proprietaire.findByNomProprietaire", query = "SELECT p FROM Proprietaire p WHERE p.nomProprietaire = :nomProprietaire"),
    @NamedQuery(name = "Proprietaire.findByPrenomProprietaire", query = "SELECT p FROM Proprietaire p WHERE p.prenomProprietaire = :prenomProprietaire")
})
public class Proprietaire implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proprietaire")
    private Integer idProprietaire;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nom_proprietaire")
    private String nomProprietaire;
    @Size(max = 100)
    @Column(name = "prenom_proprietaire")
    private String prenomProprietaire;
    @OneToMany(mappedBy = "idProprietaire", fetch = FetchType.LAZY)
    private List<Oeuvrevente> oeuvreventeList;
    @OneToMany(mappedBy = "idProprietaire", fetch = FetchType.LAZY)
    private List<Oeuvrepret> oeuvrepretList;

    public Proprietaire()
    {
    }

    public Proprietaire(Integer idProprietaire)
    {
        this.idProprietaire = idProprietaire;
    }

    public Proprietaire(Integer idProprietaire, String nomProprietaire)
    {
        this.idProprietaire = idProprietaire;
        this.nomProprietaire = nomProprietaire;
    }

    public Integer getIdProprietaire()
    {
        return idProprietaire;
    }

    public void setIdProprietaire(Integer idProprietaire)
    {
        this.idProprietaire = idProprietaire;
    }

    public String getNomProprietaire()
    {
        return nomProprietaire;
    }

    public void setNomProprietaire(String nomProprietaire)
    {
        this.nomProprietaire = nomProprietaire;
    }

    public String getPrenomProprietaire()
    {
        return prenomProprietaire;
    }

    public void setPrenomProprietaire(String prenomProprietaire)
    {
        this.prenomProprietaire = prenomProprietaire;
    }

    @XmlTransient
    public List<Oeuvrevente> getOeuvreventeList()
    {
        return oeuvreventeList;
    }

    public void setOeuvreventeList(List<Oeuvrevente> oeuvreventeList)
    {
        this.oeuvreventeList = oeuvreventeList;
    }

    @XmlTransient
    public List<Oeuvrepret> getOeuvrepretList()
    {
        return oeuvrepretList;
    }

    public void setOeuvrepretList(List<Oeuvrepret> oeuvrepretList)
    {
        this.oeuvrepretList = oeuvrepretList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idProprietaire != null ? idProprietaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Proprietaire))
        {
            return false;
        }
        Proprietaire other = (Proprietaire) object;
        if((this.idProprietaire == null && other.idProprietaire != null) || (this.idProprietaire != null && !this.idProprietaire.equals(other.idProprietaire)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.entities.Proprietaire[ idProprietaire=" + idProprietaire + " ]";
    }
    
}
