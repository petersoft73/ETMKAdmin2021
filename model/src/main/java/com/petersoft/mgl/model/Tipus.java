package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Tipus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tipusId;

    private String tipusNev;

    @OneToMany(mappedBy = "tipus")
    private List<Lepcso> lepcsoList;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public Tipus() {

    }

    public Tipus(String tipusNev) {
        this.tipusNev = tipusNev;
    }

    public int getTipusId() {
        return tipusId;
    }

    public void setTipusId(int tipusId) {
        this.tipusId = tipusId;
    }

    public String getTipusNev() {
        return tipusNev;
    }

    public void setTipusNev(String tipusNev) {
        this.tipusNev = tipusNev;
    }

    @Override
    public String toString() {
        return tipusNev;
    }

    public List<Lepcso> getLepcsoList() {
        return lepcsoList;
    }

    public void setLepcsoList(List<Lepcso> lepcsoList) {
        this.lepcsoList = lepcsoList;
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
