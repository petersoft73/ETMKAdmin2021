package com.petersoft.mgl.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.List;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Raktar {
    private Integer id;
    private List<Alkatresz> alkatreszList;
    private List<Tranzakcio> tranzakcioList;


}
