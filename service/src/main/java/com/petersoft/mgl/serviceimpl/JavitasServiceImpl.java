package com.petersoft.mgl.serviceimpl;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.JavitasService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JavitasServiceImpl implements JavitasService {


    @Override
    public List<Javitas> getJavitasList(Lepcso lepcso) {
        return DBConnector.getJavitasList().stream()
                .filter(javitas -> javitas.getLepcso().getLepcsoSzama() == lepcso.getLepcsoSzama())
                .sorted(Comparator.comparing(Javitas::getJavitasKelte).reversed())
                .collect(Collectors.toList());
    }

    public List<Javitas> getJavitasList() {
        return DBConnector.getJavitasList().stream()
                .sorted(Comparator.comparing(Javitas::getJavitasKelte).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void saveJavitas(Javitas javitas) {
        DBConnector.saveJavitas(javitas);
    }

    @Override
    public List<Javitas> getJavitasInterval(Lepcso lepcso, LocalDate startDate, LocalDate endDate) {
        List<Javitas> javitasList = DBConnector.getJavitasInterval(
                lepcso.getLepcsoSzama(), startDate, endDate);
        javitasList.sort(Comparator.comparing(Javitas::getJavitasKelte));
        return javitasList;
    }

    @Override
    public List<Javitas> getIdoszakosJavitas(LocalDate startDate, LocalDate endDate) {
        return DBConnector.getIdoszakosJavitas(startDate, endDate);
    }
}
