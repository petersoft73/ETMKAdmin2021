package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Karbantartas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int karbantartasId;

    @OneToOne
    @JoinColumn(name = "lepcsoSzama")
    private Lepcso lepcso;

    private LocalDate karbantartasDatum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karbTipusId")
    private KarbantartasTipus karbantartasTipus;

    private int muszakSzama;

    private boolean elvegezve;

    @Transient
    private int karbTipusId;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public Karbantartas() {
    }

    public Karbantartas(Lepcso lepcso, LocalDate karbantartasDatum) {

    }

    public int getKarbantartasId() {
        return karbantartasId;
    }

    public void setKarbantartasId(int karbantartasId) {
        this.karbantartasId = karbantartasId;
    }

    public Lepcso getLepcso() {
        return lepcso;
    }

    public void setLepcso(Lepcso lepcso) {
        this.lepcso = lepcso;
    }

    public LocalDate getKarbantartasDatum() {
        return karbantartasDatum;
    }

    public void setKarbantartasDatum(LocalDate karbantartasDatum) {
        this.karbantartasDatum = karbantartasDatum;
    }

    public KarbantartasTipus getKarbantartasTipus() {
        return karbantartasTipus;
    }

    public void setKarbantartasTipus(KarbantartasTipus karbantartasTipus) {
        this.karbantartasTipus = karbantartasTipus;
    }

    public boolean isElvegezve() {
        return elvegezve;
    }

    public void setElvegezve(boolean elvegezve) {
        this.elvegezve = elvegezve;
    }

    public int getMuszakSzama() {
        return muszakSzama;
    }

    public void setMuszakSzama(int muszakSzama) {
        this.muszakSzama = muszakSzama;
    }

    @Override
    public String toString() {
        return karbantartasTipus.getKarbTipusKod();
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

    public int getKarbTipusId() {
        return this.karbantartasTipus.getKarbTipusId();
    }

    public void setKarbTipusId(int karbTipusId) {
        this.karbTipusId = karbTipusId;
    }
}
