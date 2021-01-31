package com.petersoft.mgl.service;

import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.model.Location;
import com.petersoft.mgl.model.Status;
import com.petersoft.mgl.model.Tipus;

import java.util.List;

public interface LepcsoService {

    List<Lepcso> getAllLepcso() throws Throwable;

    void saveLepcso(Lepcso lepcso) throws Throwable;

    void deleteLepcso(Lepcso lepcso) throws Throwable;

    void rollback() throws Throwable;

    List<Location> getLocationList() throws Throwable;

    List<Tipus> getTipusList() throws Throwable;

    List<Status> getStatusList() throws Throwable;
}
