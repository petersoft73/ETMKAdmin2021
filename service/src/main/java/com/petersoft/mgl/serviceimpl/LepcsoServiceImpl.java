package com.petersoft.mgl.serviceimpl;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.model.Location;
import com.petersoft.mgl.model.Status;
import com.petersoft.mgl.model.Tipus;
import com.petersoft.mgl.service.LepcsoService;

import java.util.List;

public class LepcsoServiceImpl implements LepcsoService {

    public LepcsoServiceImpl() {
    }

    @Override
    public List<Lepcso> getAllLepcso() {
        return DBConnector.getLepcsoList();
    }

    @Override
    public void saveLepcso(Lepcso lepcso) {
        DBConnector.saveLepcso(lepcso);
    }

    @Override
    public void deleteLepcso(Lepcso lepcso) {
        DBConnector.deleteLepcso(lepcso);
    }

    public void rollback() {
        DBConnector.rollback();
    }

    @Override
    public List<Location> getLocationList() {
        return DBConnector.getLocationList();
    }

    @Override
    public List<Tipus> getTipusList() {
        return DBConnector.getTipusList();
    }

    public List<Status> getStatusList() {
        return DBConnector.getStatusList();
    }
}
