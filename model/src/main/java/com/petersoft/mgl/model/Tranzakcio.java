package com.petersoft.mgl.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Tranzakcio {
    private Integer id;
    private Alkatresz alkatresz;
    private Raktar raktarHonnan;
    private LocalDate datum;

//    @ManyToOne
//    private Javitas javitas;
}
