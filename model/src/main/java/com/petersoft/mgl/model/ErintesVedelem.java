package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ErintesVedelem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int erVedId;
    private LocalDate kelt;
    private LocalDate ervenyes;
    private String jegyzokonyvSzam;
    @OneToMany(mappedBy = "erintesVedelem")
    private List<Lepcso> lepcsoList;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public ErintesVedelem() {

    }

    public ErintesVedelem(LocalDate now, LocalDate date, String s) {
        this.kelt = now;
        this.ervenyes = date;
        this.jegyzokonyvSzam = s;
    }

    public ErintesVedelem(LocalDate localDate, LocalDate date) {
        this.kelt = localDate;
        this.ervenyes = date;
    }

    public int getErVedId() {
        return erVedId;
    }

    public void setErVedId(int erVedId) {
        this.erVedId = erVedId;
    }

    public LocalDate getKelt() {
        return kelt;
    }

    public void setKelt(LocalDate kelt) {
        this.kelt = kelt;
    }

    public LocalDate getErvenyes() {
        return ervenyes;
    }

    public void setErvenyes(LocalDate ervenyes) {
        this.ervenyes = ervenyes;
    }

    public String getJegyzokonyvSzam() {
        return jegyzokonyvSzam;
    }

    public void setJegyzokonyvSzam(String jegyzokonyvSzam) {
        this.jegyzokonyvSzam = jegyzokonyvSzam;
    }

    public List<Lepcso> getLepcsoList() {
        return lepcsoList;
    }

    public void setLepcsoList(List<Lepcso> lepcsoList) {
        this.lepcsoList = lepcsoList;
    }

    @Override
    public String toString() {
        return "ErintesVedelem{" +
                "erVedId=" + erVedId +
                ", kelt=" + kelt +
                ", ervenyes=" + ervenyes +
                ", jegyzokonyvSzam='" + jegyzokonyvSzam + '\'' +
                '}';
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
