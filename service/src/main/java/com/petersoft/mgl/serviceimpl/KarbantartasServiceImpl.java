package com.petersoft.mgl.serviceimpl;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.model.KarbantartasTipus;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.KarbantartasService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class KarbantartasServiceImpl implements KarbantartasService {
    @Override
    public List<KarbantartasTipus> getKarbantartasTipusList() {
        return DBConnector.getKarbantartasTipusList();
    }

    @Override
    public List<Karbantartas> getKarbantartasList(Lepcso lepcso) {
        return DBConnector.getKarbantartasList(lepcso);
    }

    @Override
    public void addKarbantartas(Karbantartas k) {
        DBConnector.addKarbantartas(k);
    }

    @Override
    public List<Lepcso> getHianyzoKarbList() {
        return DBConnector.getHianyzoKarbList();
    }

    @Override
    public void deleteKarbantartas(Karbantartas k) {
        DBConnector.deleteKarbantartas(k);
    }

    @Override
    public void updateKarbantartas(Karbantartas k) {
        DBConnector.updateKarbantartas(k);
    }

    @Override
    public List<Karbantartas> getAllKarbantartas() {
        return DBConnector.getAllKarbantartas();
    }

    public List<Karbantartas> getKarbantartasByDate(LocalDate date) {

        return DBConnector.getKarbantartasByDate(date);
    }

    @Override
    public List<Karbantartas> getKarbantartasInterval(Lepcso lepcso, LocalDate startDate, LocalDate endDate) {
        List<Karbantartas> karbantartasList = DBConnector.getKarbantartasInterval(
                lepcso.getLepcsoSzama(), startDate, endDate);
        karbantartasList.sort(Comparator.comparing(Karbantartas::getKarbantartasDatum));
        return karbantartasList;
    }

    @Override
    public List<Karbantartas> getReportHianyzoKarbList(LocalDateTime startDate, LocalDateTime endDate) {
        return DBConnector.getReportHianyzoKarbList(startDate, endDate);
    }
}
