package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;

@Entity
public class Javitas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int javitasId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Lepcso lepcso;

    @ManyToOne
    @JoinColumn(name = "hibaId")
    private Hiba hiba;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private String leiras;
    private LocalDate javitasKelte;
  //  private Status uzemkepes;
    private int muszakSzama;

    public Javitas() {

    }

    public Javitas(Lepcso lepcso, String leiras, LocalDate javitasKelte, Status uzemkepes, int muszakSzama) {
        this.lepcso = lepcso;
        this.leiras = leiras;
        this.javitasKelte = javitasKelte;
        this.lepcso.setStatus(uzemkepes);
        this.muszakSzama = muszakSzama;
        //  setLepcsoUzemkepes(uzemkepes);
    }

    public Javitas(Lepcso lepcso, String leiras, LocalDate javitasKelte, int muszakSzama, Hiba hiba) {
        this.lepcso = lepcso;
        this.leiras = leiras;
        if (hiba != null) {
            this.hiba = hiba;
        }
        this.javitasKelte = javitasKelte;
        this.muszakSzama = muszakSzama;
        //  this.lepcso.setStatus(uzemkepes);
        //   setLepcsoUzemkepes(uzemkepes);

    }

    public int getJavitasId() {
        return javitasId;
    }

    public void setJavitasId(int javitasId) {
        this.javitasId = javitasId;
    }

    public Lepcso getLepcso() {
        return lepcso;
    }

    public void setLepcso(Lepcso lepcso) {
        this.lepcso = lepcso;
    }

    public Hiba getHiba() {
        return hiba;
    }

    public void setHiba(Hiba hiba) {
        this.hiba = hiba;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public LocalDate getJavitasKelte() {
        return javitasKelte;
    }

    public void setJavitasKelte(LocalDate javitasKelte) {
        this.javitasKelte = javitasKelte;
    }

    public Status isLepcsoUzemkepes() {
        return lepcso.getStatus();
    }

    public void setLepcsoUzemkepes(Status uzemkepes) {

        lepcso.setStatus(uzemkepes);
    }

    public int getMuszakSzama() {
        return muszakSzama;
    }

    public void setMuszakSzama(int muszakSzama) {
        this.muszakSzama = muszakSzama;
    }

    @Override
    public String toString() {
        return leiras +
                javitasKelte;
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

//    public Status getUzemkepes() {
//        return uzemkepes;
//    }
//
//    public void setUzemkepes(Status uzemkepes) {
//        this.uzemkepes = uzemkepes;
//    }
}

