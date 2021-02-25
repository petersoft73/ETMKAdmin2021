package com.petersoft.mgl.leltar;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Alkatresz;

import java.util.List;

public class AlkatreszFrissitClass {
    private Alkatresz alkatresz;
    private List<Alkatresz> alkatreszList;

    public AlkatreszFrissitClass(List<Alkatresz> alkatreszList) {

    }

    public static void removeItems(List<Alkatresz> alkatreszList) {
        alkatreszList.forEach(a -> DBConnector.deleteAlkatresz(a));
    }

    public static void addItems(List<Alkatresz> alkatreszList) {
        alkatreszList.forEach(a -> DBConnector.saveAlkatresz(a));
    }
}
