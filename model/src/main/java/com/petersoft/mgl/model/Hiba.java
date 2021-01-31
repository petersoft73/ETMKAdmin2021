package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Hiba {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hibaId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Lepcso lepcso;
    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
    private LocalDate datum;
    private String leiras;

    public Hiba() {

    }

    public Hiba(Lepcso lepcso, LocalDate datum, String leiras) {
        this.lepcso = lepcso;
        this.datum = datum;
        this.leiras = leiras;
    }

    public int getHibaId() {
        return hibaId;
    }

    public void setHibaId(int hibaId) {
        this.hibaId = hibaId;
    }

    public Lepcso getLepcso() {
        return lepcso;
    }

    public void setLepcso(Lepcso lepcso) {
        this.lepcso = lepcso;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    @Override
    public String toString() {
        return leiras + " " + getDatum();
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
