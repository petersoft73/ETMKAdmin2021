package com.petersoft.mgl.dolgozo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Dolgozo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nev;
    private boolean aktiv;
    private Integer turSzama;
    private Integer hrAzonosito;
    private boolean csopVez;
    @OneToMany(mappedBy = "dolgozo")
    private List<DolgozoNapiRekord> dolgozoNapiRekordList;

    public Dolgozo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public Integer getTurSzama() {
        return turSzama;
    }

    public void setTurSzama(Integer turSzama) {
        this.turSzama = turSzama;
    }

    public boolean isCsopVez() {
        return csopVez;
    }

    public void setCsopVez(boolean csopVez) {
        this.csopVez = csopVez;
    }

    public Integer getHrAzonosito() {
        return hrAzonosito;
    }

    public void setHrAzonosito(Integer hrAzonosito) {
        this.hrAzonosito = hrAzonosito;
    }

    public List<DolgozoNapiRekord> getDolgozoNapiRekordList() {
        return dolgozoNapiRekordList;
    }

    public void setDolgozoNapiRekordList(List<DolgozoNapiRekord> dolgozoNapiRekordList) {
        this.dolgozoNapiRekordList = dolgozoNapiRekordList;
    }

    @Override
    public String toString() {
        return nev + "   " +  turSzama + ".túr";
    }
}
