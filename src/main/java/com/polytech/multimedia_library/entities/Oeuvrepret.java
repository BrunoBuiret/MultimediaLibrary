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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
@Entity
@Table(name = "oeuvrepret")
@XmlRootElement
public class Oeuvrepret implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_oeuvrepret")
    private Integer idOeuvrepret;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "titre_oeuvrepret")
    private String titreOeuvrepret;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oeuvrepret", fetch = FetchType.LAZY)
    private List<Emprunt> empruntList;

    @JoinColumn(name = "id_proprietaire", referencedColumnName = "id_proprietaire")
    @ManyToOne(fetch = FetchType.LAZY)
    private Proprietaire idProprietaire;

    public Oeuvrepret()
    {
    }

    public Oeuvrepret(Integer idOeuvrepret)
    {
        this.idOeuvrepret = idOeuvrepret;
    }

    public Oeuvrepret(Integer idOeuvrepret, String titreOeuvrepret)
    {
        this.idOeuvrepret = idOeuvrepret;
        this.titreOeuvrepret = titreOeuvrepret;
    }

    public Integer getIdOeuvrepret()
    {
        return idOeuvrepret;
    }

    public void setIdOeuvrepret(Integer idOeuvrepret)
    {
        this.idOeuvrepret = idOeuvrepret;
    }

    public String getTitreOeuvrepret()
    {
        return titreOeuvrepret;
    }

    public void setTitreOeuvrepret(String titreOeuvrepret)
    {
        this.titreOeuvrepret = titreOeuvrepret;
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

    public Proprietaire getIdProprietaire()
    {
        return idProprietaire;
    }

    public void setIdProprietaire(Proprietaire idProprietaire)
    {
        this.idProprietaire = idProprietaire;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idOeuvrepret != null ? idOeuvrepret.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Oeuvrepret))
        {
            return false;
        }
        Oeuvrepret other = (Oeuvrepret) object;
        if((this.idOeuvrepret == null && other.idOeuvrepret != null) || (this.idOeuvrepret != null && !this.idOeuvrepret.equals(other.idOeuvrepret)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.Oeuvrepret[ idOeuvrepret=" + idOeuvrepret + " ]";
    }
}
