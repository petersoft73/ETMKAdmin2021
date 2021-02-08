package com.petersoft.mgl.model;

import com.petersoft.mgl.leltar.Keszlet;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alkatresz {
    @Id
    private Integer id;
    private String leiras;
    private String tipus;
    private String cikkszam;
    private String mennyEgyseg;
  //  private Keszlet keszlet;
    private String hely;
    private Integer darabszam;

    public Alkatresz() {
    }


    public Alkatresz(String leiras, String hely) {
        this.hely = hely;
        this.leiras = leiras;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getHely() {
        return hely;
    }

    public void setHely(String hely) {
        this.hely = hely;
    }

    public Integer getDarabszam() {
        return darabszam;
    }

    public void setDarabszam(Integer darabszam) {
        this.darabszam = darabszam;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getCikkszam() {
        return cikkszam;
    }

    public void setCikkszam(String cikkszam) {
        this.cikkszam = cikkszam;
    }

    @Override
    public String toString() {
        return "Alkatresz{" +
                "leiras='" + leiras + '\'' +
                ", hely='" + hely + '\'' +
                ", darabszam=" + darabszam +
                '}';
    }
}
