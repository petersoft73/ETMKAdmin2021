package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Lepcso {
    @Id
    private int lepcsoSzama;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
    private Location location;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipusId")
    private Tipus tipus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId")
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "erVedId")
    private ErintesVedelem erintesVedelem;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;


    public Lepcso() {
    }


    public Lepcso(int lepcsoSzama, Location location) {
        this.lepcsoSzama = lepcsoSzama;
        this.location = location;
    }

    public Lepcso(int lepcsoSzama, Location location, Tipus tipus) {
        this.lepcsoSzama = lepcsoSzama;
        this.location = location;
        this.tipus = tipus;

    }


    public Lepcso(int lepcsoSzama, Location location, Tipus tipus, ErintesVedelem erintesVedelem) {
        this.lepcsoSzama = lepcsoSzama;
        this.location = location;
        this.tipus = tipus;
        this.erintesVedelem = erintesVedelem;
    }

    public int getLepcsoSzama() {
        return lepcsoSzama;
    }

    public void setLepcsoSzama(int lepcsoSzama) {
        this.lepcsoSzama = lepcsoSzama;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Tipus getTipus() {
        return tipus;
    }

    public void setTipus(Tipus tipus) {
        this.tipus = tipus;
    }

    public ErintesVedelem getErintesVedelem() {
        return erintesVedelem;
    }

    public void setErintesVedelem(ErintesVedelem erintesVedelem) {
        this.erintesVedelem = erintesVedelem;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUzemkepesString() {
        return status.toString();
    }

    @Override
    public String toString() {
        return lepcsoSzama
                + ""
//                " : : " + getLocation() +
//                " : : " + getTipus().getTipusNev()
                ;
    }

    public String karbListString() {
        return lepcsoSzama +
                " : : " + getLocation() +
                " : : " + getTipus().getTipusNev();
      //  return String.valueOf(lepcsoSzama);
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
