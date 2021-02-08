package com.petersoft.mgl.leltar;

import com.petersoft.mgl.model.Alkatresz;

import java.time.LocalDate;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Tranzakcio {
    private Integer id;
    private Alkatresz alkatresz;
    private RaktarHely raktarHonnan;
    private LocalDate datum;
    private Integer darabszam;

//    @ManyToOne
//    private Javitas javitas;
}
