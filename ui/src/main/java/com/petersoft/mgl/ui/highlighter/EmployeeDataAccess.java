package com.petersoft.mgl.ui.highlighter;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Hiba;

import java.util.List;

public class EmployeeDataAccess {

    public static List<Hiba> getHiba() {
        List<Hiba> list;
        list = DBConnector.getHibaList();
        return list;
    }
}