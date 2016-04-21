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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "reservation")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findByIdOeuvrevente", query = "SELECT r FROM Reservation r WHERE r.reservationPK.idOeuvrevente = :idOeuvrevente"),
    @NamedQuery(name = "Reservation.findByIdAdherent", query = "SELECT r FROM Reservation r WHERE r.reservationPK.idAdherent = :idAdherent"),
    @NamedQuery(name = "Reservation.findByDateReservation", query = "SELECT r FROM Reservation r WHERE r.dateReservation = :dateReservation"),
    @NamedQuery(name = "Reservation.findByStatut", query = "SELECT r FROM Reservation r WHERE r.statut = :statut")
})
public class Reservation implements Serializable
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservationPK reservationPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_reservation")
    @Temporal(TemporalType.DATE)
    private Date dateReservation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "statut")
    private String statut;
    @JoinColumn(name = "id_oeuvrevente", referencedColumnName = "id_oeuvrevente", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Oeuvrevente oeuvrevente;
    @JoinColumn(name = "id_adherent", referencedColumnName = "id_adherent", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Adherent adherent;

    public Reservation()
    {
    }

    public Reservation(ReservationPK reservationPK)
    {
        this.reservationPK = reservationPK;
    }

    public Reservation(ReservationPK reservationPK, Date dateReservation, String statut)
    {
        this.reservationPK = reservationPK;
        this.dateReservation = dateReservation;
        this.statut = statut;
    }

    public Reservation(int idOeuvrevente, int idAdherent)
    {
        this.reservationPK = new ReservationPK(idOeuvrevente, idAdherent);
    }

    public ReservationPK getReservationPK()
    {
        return reservationPK;
    }

    public void setReservationPK(ReservationPK reservationPK)
    {
        this.reservationPK = reservationPK;
    }

    public Date getDateReservation()
    {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation)
    {
        this.dateReservation = dateReservation;
    }

    public String getStatut()
    {
        return statut;
    }

    public void setStatut(String statut)
    {
        this.statut = statut;
    }

    public Oeuvrevente getOeuvrevente()
    {
        return oeuvrevente;
    }

    public void setOeuvrevente(Oeuvrevente oeuvrevente)
    {
        this.oeuvrevente = oeuvrevente;
    }

    public Adherent getAdherent()
    {
        return adherent;
    }

    public void setAdherent(Adherent adherent)
    {
        this.adherent = adherent;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (reservationPK != null ? reservationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Reservation))
        {
            return false;
        }
        Reservation other = (Reservation) object;
        if((this.reservationPK == null && other.reservationPK != null) || (this.reservationPK != null && !this.reservationPK.equals(other.reservationPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.polytech.multimedia_library.entities.Reservation[ reservationPK=" + reservationPK + " ]";
    }
    
}
