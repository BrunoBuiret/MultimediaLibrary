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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "oeuvrevente")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Oeuvrevente.findAll", query = "SELECT o FROM Oeuvrevente o")
})
public class Oeuvrevente implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_oeuvrevente")
    private Integer idOeuvrevente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "titre_oeuvrevente")
    private String titreOeuvrevente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "etat_oeuvrevente")
    private String etatOeuvrevente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prix_oeuvrevente")
    private float prixOeuvrevente;
    @JoinColumn(name = "id_proprietaire", referencedColumnName = "id_proprietaire")
    @ManyToOne(fetch = FetchType.LAZY)
    private Proprietaire idProprietaire;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oeuvrevente", fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    public Oeuvrevente()
    {
    }

    public Oeuvrevente(Integer idOeuvrevente)
    {
        this.idOeuvrevente = idOeuvrevente;
    }

    public Oeuvrevente(Integer idOeuvrevente, String titreOeuvrevente, String etatOeuvrevente, float prixOeuvrevente)
    {
        this.idOeuvrevente = idOeuvrevente;
        this.titreOeuvrevente = titreOeuvrevente;
        this.etatOeuvrevente = etatOeuvrevente;
        this.prixOeuvrevente = prixOeuvrevente;
    }

    public Integer getIdOeuvrevente()
    {
        return idOeuvrevente;
    }

    public void setIdOeuvrevente(Integer idOeuvrevente)
    {
        this.idOeuvrevente = idOeuvrevente;
    }

    public String getTitreOeuvrevente()
    {
        return titreOeuvrevente;
    }

    public void setTitreOeuvrevente(String titreOeuvrevente)
    {
        this.titreOeuvrevente = titreOeuvrevente;
    }

    public String getEtatOeuvrevente()
    {
        return etatOeuvrevente;
    }

    public void setEtatOeuvrevente(String etatOeuvrevente)
    {
        this.etatOeuvrevente = etatOeuvrevente;
    }

    public float getPrixOeuvrevente()
    {
        return prixOeuvrevente;
    }

    public void setPrixOeuvrevente(float prixOeuvrevente)
    {
        this.prixOeuvrevente = prixOeuvrevente;
    }

    public Proprietaire getIdProprietaire()
    {
        return idProprietaire;
    }

    public void setIdProprietaire(Proprietaire idProprietaire)
    {
        this.idProprietaire = idProprietaire;
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
        hash += (idOeuvrevente != null ? idOeuvrevente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Oeuvrevente))
        {
            return false;
        }
        Oeuvrevente other = (Oeuvrevente) object;
        if((this.idOeuvrevente == null && other.idOeuvrevente != null) || (this.idOeuvrevente != null && !this.idOeuvrevente.equals(other.idOeuvrevente)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.Oeuvrevente[ idOeuvrevente=" + idOeuvrevente + " ]";
    }
    
}
