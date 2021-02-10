package com.petersoft.mgl.dolgozo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class DolgozoNapiRekord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDate munkaNap;
    @Enumerated(EnumType.STRING)
    private StatuszEnum statuszEnum;
    @Enumerated(EnumType.STRING)
    private MuszakEnum muszakEnum;
    @ManyToOne
    private Dolgozo dolgozo;

    public DolgozoNapiRekord() {
    }


    public DolgozoNapiRekord(Dolgozo dolgozo,
                             StatuszEnum statuszEnum,
                             MuszakEnum muszakEnum,
                             LocalDate munkaNap) {
        this.dolgozo = dolgozo;
        this.statuszEnum = statuszEnum;
        this.muszakEnum = muszakEnum;
        this.munkaNap = munkaNap;
    }

    public LocalDate getMunkaNap() {
        return munkaNap;
    }

    public void setMunkaNap(LocalDate munkaNap) {
        this.munkaNap = munkaNap;
    }

    public StatuszEnum getStatuszEnum() {
        return statuszEnum;
    }

    public void setStatuszEnum(StatuszEnum statuszEnum) {
        this.statuszEnum = statuszEnum;
    }

    public MuszakEnum getMuszakEnum() {
        return muszakEnum;
    }

    public void setMuszakEnum(MuszakEnum muszakEnum) {
        this.muszakEnum = muszakEnum;
    }

    public Dolgozo getDolgozo() {
        return dolgozo;
    }

    public void setDolgozo(Dolgozo dolgozo) {
        this.dolgozo = dolgozo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DolgozoNapiRekord{" +
                "id=" + id +
                ", munkaNap=" + munkaNap +
                ", statuszEnum=" + statuszEnum +
                ", muszakEnum=" + muszakEnum +
                ", dolgozo=" + dolgozo +
                '}';
    }

    public enum StatuszEnum {
        DOLGOZIK("Dolgozik"),
        PLUSZ_NAP("Plusz nap"),
        SZABADSAG("Szabadság"),
        BETEG("Beteg"),
        SZABADNAP("Szabadnap"),
        ISMERETLEN("Válassz...");

        StatuszEnum(String statusz) {
            this.statusz = statusz;
        }

        private String  statusz;

        public String getStatusz() {
            return statusz;
        }

        public void setStatusz(String statusz) {
            this.statusz = statusz;
        }

        @Override
        public String toString() {
            return statusz;
        }
    }

    public enum MuszakEnum {
        NAPPAL("06:00 - 18:00"),
        EJJEL("18:00 - 06:00"),
        CSOP_VEZ_NAPPAL("06:00  - 14.20"),
        CSOP_VEZ_EJJEL("22:00 - 06:20"),
        PIHENO("Pihenőnap"),
        NEM_DOLGOZIK("Nem dolgozik"),
        ISMERETLEN("Válassz...");

        MuszakEnum(String muszak) {
            this.muszak = muszak;
        }

        private String muszak;

        public String getMuszak() {
            return muszak;
        }

        public void setMuszak(String muszak) {
            this.muszak = muszak;
        }

        @Override
        public String toString() {
            return muszak;
        }
    }
}
