package com.petersoft.mgl.dto;

import java.time.LocalDate;

public class LepcsoHistoryDTO {
    private int lepcsoSzama;
    private String javitasLeiras;
    private int muszakSzama;
    private LocalDate javitasDatum;
    private LocalDate startDatum;
    private LocalDate endDatum;

    public int getLepcsoSzama() {
        return lepcsoSzama;
    }

    public void setLepcsoSzama(int lepcsoSzama) {
        this.lepcsoSzama = lepcsoSzama;
    }

    public String getJavitasLeiras() {
        return javitasLeiras;
    }

    public void setJavitasLeiras(String javitasLeiras) {
        this.javitasLeiras = javitasLeiras;
    }

    public int getMuszakSzama() {
        return muszakSzama;
    }

    public void setMuszakSzama(int muszakSzama) {
        this.muszakSzama = muszakSzama;
    }

    public LocalDate getJavitasDatum() {
        return javitasDatum;
    }

    public void setJavitasDatum(LocalDate javitasDatum) {
        this.javitasDatum = javitasDatum;
    }

    public LocalDate getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(LocalDate startDatum) {
        this.startDatum = startDatum;
    }

    public LocalDate getEndDatum() {
        return endDatum;
    }

    public void setEndDatum(LocalDate endDatum) {
        this.endDatum = endDatum;
    }
}
