package com.petersoft.mgl.model;

import com.petersoft.mgl.leltar.Keszlet;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "seq", initialValue = 1)
public class Alkatresz {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id
    private Integer id;
    private String leiras = "";
    private String tipus = "";
    private String cikkszam = "";
    private String egyseg = "";
  //  private Keszlet keszlet;
    private String hely = "";
    private Integer darabszam = 0;
    private String megjegyzes = "";

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
        return cikkszam == null ? "" : cikkszam;
    }

    public void setCikkszam(String cikkszam) {
        this.cikkszam = cikkszam;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }

    public String getEgyseg() {
        return egyseg;
    }

    public void setEgyseg(String egyseg) {
        this.egyseg = egyseg;
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
