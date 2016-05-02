/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polytech.multimedia_library.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "adherent")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Adherent.findAll", query = "SELECT a FROM Adherent a")
})
public class Adherent implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adherent")
    private Integer idAdherent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nom_adherent")
    private String nomAdherent;
    @Size(max = 100)
    @Column(name = "prenom_adherent")
    private String prenomAdherent;
    @Size(max = 100)
    @Column(name = "ville_adherent")
    private String villeAdherent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adherent", fetch = FetchType.LAZY)
    private List<Emprunt> empruntList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adherent", fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    public Adherent()
    {
    }

    public Adherent(Integer idAdherent)
    {
        this.idAdherent = idAdherent;
    }

    public Adherent(Integer idAdherent, String nomAdherent)
    {
        this.idAdherent = idAdherent;
        this.nomAdherent = nomAdherent;
    }

    public Integer getIdAdherent()
    {
        return idAdherent;
    }

    public void setIdAdherent(Integer idAdherent)
    {
        this.idAdherent = idAdherent;
    }

    public String getNomAdherent()
    {
        return nomAdherent;
    }

    public void setNomAdherent(String nomAdherent)
    {
        this.nomAdherent = nomAdherent;
    }

    public String getPrenomAdherent()
    {
        return prenomAdherent;
    }

    public void setPrenomAdherent(String prenomAdherent)
    {
        this.prenomAdherent = prenomAdherent;
    }
    
    public String getFullName()
    {
        return this.prenomAdherent + " " + this.nomAdherent;
    }

    public String getVilleAdherent()
    {
        return villeAdherent;
    }

    public void setVilleAdherent(String villeAdherent)
    {
        this.villeAdherent = villeAdherent;
    }

    @XmlTransient
    public List<Emprunt> getEmpruntList()
    {
        return empruntList;
    }

    public void setEmpruntList(List<Emprunt> empruntList)
    {
        this.empruntList = empruntList;
    }

    @XmlTransient
    public List<Reservation> getReservationList()
    {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList)
    {
        this.reservationList = reservationList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idAdherent != null ? idAdherent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Adherent))
        {
            return false;
        }
        Adherent other = (Adherent) object;
        if((this.idAdherent == null && other.idAdherent != null) || (this.idAdherent != null && !this.idAdherent.equals(other.idAdherent)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.Adherent[ idAdherent=" + idAdherent + " ]";
    }
    
}
