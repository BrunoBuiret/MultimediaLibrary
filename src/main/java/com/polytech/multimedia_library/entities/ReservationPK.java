package com.polytech.multimedia_library.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 */
@Embeddable
public class ReservationPK implements Serializable
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oeuvrevente", nullable = false, insertable = false, updatable = false)
    private Oeuvrevente oeuvrevente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adherent", nullable = false, insertable = false, updatable = false)
    private Adherent adherent;

    public ReservationPK()
    {
    }

    public ReservationPK(Oeuvrevente oeuvrevente, Adherent adherent)
    {
        this.oeuvrevente = oeuvrevente;
        this.adherent = adherent;
    }

    public Oeuvrevente getOeuvrevente()
    {
        return this.oeuvrevente;
    }

    public void setOeuvrevente(Oeuvrevente oeuvrevente)
    {
        this.oeuvrevente = oeuvrevente;
    }

    public Adherent getAdherent()
    {
        return this.adherent;
    }

    public void setAdherent(Adherent adherent)
    {
        this.adherent = adherent;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) this.oeuvrevente.getIdOeuvrevente();
        hash += (int) this.adherent.getIdAdherent();
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof ReservationPK))
        {
            return false;
        }

        ReservationPK other = (ReservationPK) object;

        return this.oeuvrevente.equals(other.oeuvrevente) && this.adherent.equals(other.adherent);
    }

    @Override
    public String toString()
    {
        return
            "com.polytech.multimedia_library.ReservationPK[ idOeuvrevente=" +
            this.oeuvrevente.getIdOeuvrevente() +
            ", idAdherent=" +
            this.adherent.getIdAdherent() +
            " ]"
        ;
    }
}
