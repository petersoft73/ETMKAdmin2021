package com.petersoft.mgl.dto;

public class LepcsoDTO {
    private int lepcsoSzama;
    private String locationName;
    private String tipusNev;

    public LepcsoDTO(int lepcsoSzama, String locationName, String tipusNev) {
        this.lepcsoSzama = lepcsoSzama;
        this.locationName = locationName;
        this.tipusNev = tipusNev;
    }


    public int getLepcsoSzama() {
        return lepcsoSzama;
    }

    public void setLepcsoSzama(int lepcsoSzama) {
        this.lepcsoSzama = lepcsoSzama;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTipusNev() {
        return tipusNev;
    }

    public void setTipusNev(String tipusNev) {
        this.tipusNev = tipusNev;
    }
}
