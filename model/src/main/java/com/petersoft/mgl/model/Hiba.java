package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Hiba {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hibaId;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @OneToMany(mappedBy = "hiba")
    private List<Javitas> javitasList;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
    private String leiras;

    public Hiba() {
    }

    public Hiba(String leiras) {
        this.leiras = leiras;
    }

    public int getHibaId() {
        return hibaId;
    }

    public void setHibaId(int hibaId) {
        this.hibaId = hibaId;
    }


    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    @Override
    public String toString() {
        return leiras;
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

    public List<Javitas> getJavitasList() {
        return javitasList;
    }

    public void setJavitasList(List<Javitas> javitasList) {
        this.javitasList = javitasList;
    }
}
