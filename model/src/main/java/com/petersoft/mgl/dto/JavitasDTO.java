package com.petersoft.mgl.dto;

import com.petersoft.mgl.dolgozo.Dolgozo;
import com.petersoft.mgl.dolgozo.DolgozoNapiRekord;

import java.time.LocalDate;
import java.util.List;

public class JavitasDTO {
    private int lepcso_lepcsoSzama;
    private String leiras;
    private int muszakSzama;
    private LocalDate javitasKelte;
    private Dolgozo dolgozo;
    private List<DolgozoNapiRekord> napiRekord;

    public JavitasDTO(int lepcso_lepcsoSzama, String leiras, int muszakSzama, LocalDate javitasKelte) {
        this.lepcso_lepcsoSzama = lepcso_lepcsoSzama;
        this.leiras = leiras;
        this.muszakSzama = muszakSzama;
        this.javitasKelte = javitasKelte;
    }

    public JavitasDTO(LocalDate javitasKelte, List<DolgozoNapiRekord> napiRekord) {
        this.javitasKelte = javitasKelte;
        this.napiRekord = napiRekord;
    }

    public int getLepcso_lepcsoSzama() {
        return lepcso_lepcsoSzama;
    }

    public void setLepcso_lepcsoSzama(int lepcso_lepcsoSzama) {
        this.lepcso_lepcsoSzama = lepcso_lepcsoSzama;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public int getMuszakSzama() {
        return muszakSzama;
    }

    public void setMuszakSzama(int muszakSzama) {
        this.muszakSzama = muszakSzama;
    }

    public LocalDate getJavitasKelte() {
        return javitasKelte;
    }

    public void setJavitasKelte(LocalDate javitasKelte) {
        this.javitasKelte = javitasKelte;
    }

    public Dolgozo getDolgozo() {
        return dolgozo;
    }

    public void setDolgozo(Dolgozo dolgozo) {
        this.dolgozo = dolgozo;
    }

    public List<DolgozoNapiRekord> getNapiRekord() {
        return napiRekord;
    }

    public void setNapiRekord(List<DolgozoNapiRekord> napiRekord) {
        this.napiRekord = napiRekord;
    }

    @Override
    public String toString() {
        return "JavitasDTO{" +
                "lepcso_lepcsoSzama=" + lepcso_lepcsoSzama +
                ", leiras='" + leiras + '\'' +
                ", muszakSzama=" + muszakSzama +
                '}';
    }
}
