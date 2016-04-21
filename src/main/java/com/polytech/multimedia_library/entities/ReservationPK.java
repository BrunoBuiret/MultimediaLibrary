/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polytech.multimedia_library.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author bruno
 */
@Embeddable
public class ReservationPK implements Serializable
{
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_oeuvrevente")
    private int idOeuvrevente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_adherent")
    private int idAdherent;

    public ReservationPK()
    {
    }

    public ReservationPK(int idOeuvrevente, int idAdherent)
    {
        this.idOeuvrevente = idOeuvrevente;
        this.idAdherent = idAdherent;
    }

    public int getIdOeuvrevente()
    {
        return idOeuvrevente;
    }

    public void setIdOeuvrevente(int idOeuvrevente)
    {
        this.idOeuvrevente = idOeuvrevente;
    }

    public int getIdAdherent()
    {
        return idAdherent;
    }

    public void setIdAdherent(int idAdherent)
    {
        this.idAdherent = idAdherent;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) idOeuvrevente;
        hash += (int) idAdherent;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof ReservationPK))
        {
            return false;
        }
        ReservationPK other = (ReservationPK) object;
        if(this.idOeuvrevente != other.idOeuvrevente)
        {
            return false;
        }
        if(this.idAdherent != other.idAdherent)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.entities.ReservationPK[ idOeuvrevente=" + idOeuvrevente + ", idAdherent=" + idAdherent + " ]";
    }
    
}
