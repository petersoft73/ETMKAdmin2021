package com.petersoft.mgl.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class KarbantartasTipus {
    @Id
    private int karbTipusId;
    private String karbTipusKod;
    private String karbTipusLeiras;

    @OneToMany(mappedBy = "karbantartasTipus")
    private List<Karbantartas> karbantartasList;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public KarbantartasTipus() {

    }

    public int getKarbTipusId() {
        return karbTipusId;
    }

    @Override
    public String toString() {
        return karbTipusKod;
    }

    public void setKarbTipusId(int karbTipusId) {
        this.karbTipusId = karbTipusId;
    }

    public String getKarbTipusKod() {
        return karbTipusKod;
    }

    public void setKarbTipusKod(String karbTipusKod) {
        this.karbTipusKod = karbTipusKod;
    }

    public String getKarbTipusLeiras() {
        return karbTipusLeiras;
    }

    public void setKarbTipusLeiras(String karbTipusLeiras) {
        this.karbTipusLeiras = karbTipusLeiras;
    }

    public List<Karbantartas> getKarbantartasList() {
        return karbantartasList;
    }

    public void setKarbantartasList(List<Karbantartas> karbantartasList) {
        this.karbantartasList = karbantartasList;
    }

}
