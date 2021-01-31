package com.petersoft.mgl.serviceimpl;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Alkatresz;
import com.petersoft.mgl.service.AlkatreszService;

import java.util.List;

public class AlkatreszServiceImpl implements AlkatreszService {


    @Override
    public List<Alkatresz> getAlkatreszList() {
        return DBConnector.getAlkatreszList();
    }
}
