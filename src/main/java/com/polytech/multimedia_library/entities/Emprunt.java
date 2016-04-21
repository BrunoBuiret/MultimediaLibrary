/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polytech.multimedia_library.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "emprunt")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Emprunt.findAll", query = "SELECT e FROM Emprunt e")
})
public class Emprunt implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_emprunt")
    private Integer idEmprunt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @JoinColumn(name = "id_adherent", referencedColumnName = "id_adherent")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Adherent idAdherent;
    @JoinColumn(name = "id_oeuvrepret", referencedColumnName = "id_oeuvrepret")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Oeuvrepret idOeuvrepret;

    public Emprunt()
    {
    }

    public Emprunt(Integer idEmprunt)
    {
        this.idEmprunt = idEmprunt;
    }

    public Emprunt(Integer idEmprunt, Date dateDebut, Date dateFin)
    {
        this.idEmprunt = idEmprunt;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Integer getIdEmprunt()
    {
        return idEmprunt;
    }

    public void setIdEmprunt(Integer idEmprunt)
    {
        this.idEmprunt = idEmprunt;
    }

    public Date getDateDebut()
    {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut)
    {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin()
    {
        return dateFin;
    }

    public void setDateFin(Date dateFin)
    {
        this.dateFin = dateFin;
    }

    public Adherent getIdAdherent()
    {
        return idAdherent;
    }

    public void setIdAdherent(Adherent idAdherent)
    {
        this.idAdherent = idAdherent;
    }

    public Oeuvrepret getIdOeuvrepret()
    {
        return idOeuvrepret;
    }

    public void setIdOeuvrepret(Oeuvrepret idOeuvrepret)
    {
        this.idOeuvrepret = idOeuvrepret;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idEmprunt != null ? idEmprunt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Emprunt))
        {
            return false;
        }
        Emprunt other = (Emprunt) object;
        if((this.idEmprunt == null && other.idEmprunt != null) || (this.idEmprunt != null && !this.idEmprunt.equals(other.idEmprunt)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.Emprunt[ idEmprunt=" + idEmprunt + " ]";
    }
    
}
