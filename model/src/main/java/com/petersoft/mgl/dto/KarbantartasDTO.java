package com.petersoft.mgl.dto;

import java.time.LocalDate;

public class KarbantartasDTO {
    private int lepcsoSzama;
    private int karbTipusId;
    private int muszakSzama;
    private boolean elvegezve;
    private LocalDate karbantartasDatum;
    private String karbTipusLeiras;


    public KarbantartasDTO(int lepcsoSzama,
                           int karbTipusId,
                           int muszakSzama,
                           boolean elvegezve,
                           LocalDate karbantartasDatum) {
        this.lepcsoSzama = lepcsoSzama;
        this.karbTipusId = karbTipusId;
        this.muszakSzama = muszakSzama;
        this.elvegezve = elvegezve;
        this.karbantartasDatum = karbantartasDatum;
    }

    public KarbantartasDTO(int lepcsoSzama, String karbTipusLeiras) {
        this.lepcsoSzama = lepcsoSzama;
        this.karbTipusLeiras = karbTipusLeiras;
    }

    public KarbantartasDTO(int lepcsoSzama, String karbTipusLeiras, LocalDate karbantartasDatum, int muszakSzama) {
        this(lepcsoSzama, karbTipusLeiras);
        this.karbantartasDatum = karbantartasDatum;
        this.muszakSzama = muszakSzama;
    }

    public int getLepcsoSzama() {
        return lepcsoSzama;
    }

    public void setLepcsoSzama(int lepcsoSzama) {
        this.lepcsoSzama = lepcsoSzama;
    }

    public int getKarbTipusId() {
        return karbTipusId;
    }

    public void setKarbTipusId(int karbTipusId) {
        this.karbTipusId = karbTipusId;
    }

    public int getMuszakSzama() {
        return muszakSzama;
    }

    public void setMuszakSzama(int muszakSzama) {
        this.muszakSzama = muszakSzama;
    }

    public boolean isElvegezve() {
        return elvegezve;
    }

    public void setElvegezve(boolean elvegezve) {
        this.elvegezve = elvegezve;
    }

    public LocalDate getKarbantartasDatum() {
        return karbantartasDatum;
    }

    public void setKarbantartasDatum(LocalDate karbantartasDatum) {
        this.karbantartasDatum = karbantartasDatum;
    }

    public String getKarbTipusLeiras() {
        return karbTipusLeiras;
    }

    public void setKarbTipusLeiras(String karbTipusLeiras) {
        this.karbTipusLeiras = karbTipusLeiras;
    }

    @Override
    public String toString() {
        return "KarbantartasDTO{" +
                " karbantartasDatum=" + karbantartasDatum +
                ", lepcsoSzama=" + lepcsoSzama +
                ", karbTipusLeiras='" + karbTipusLeiras + '\'' +
                '}';
    }
}
