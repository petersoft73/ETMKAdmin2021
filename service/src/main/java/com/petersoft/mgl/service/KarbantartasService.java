package com.petersoft.mgl.service;

import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.model.KarbantartasTipus;
import com.petersoft.mgl.model.Lepcso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface KarbantartasService {

    List<KarbantartasTipus> getKarbantartasTipusList();

    List<Karbantartas> getKarbantartasList(Lepcso lepcso);

    void addKarbantartas(Karbantartas k);

    List<Lepcso> getHianyzoKarbList();

    void deleteKarbantartas(Karbantartas k);

    void updateKarbantartas(Karbantartas k);

    List<Karbantartas> getAllKarbantartas();

    List<Karbantartas> getKarbantartasByDate(LocalDate date);

    List<Karbantartas> getKarbantartasInterval(
            Lepcso lepcso,
            LocalDate startDate,
            LocalDate endDate);

    List<Karbantartas> getReportHianyzoKarbList(LocalDateTime startDate, LocalDateTime endDate);
}
