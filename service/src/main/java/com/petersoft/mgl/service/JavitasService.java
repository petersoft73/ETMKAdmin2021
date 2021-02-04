package com.petersoft.mgl.service;

import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Lepcso;

import java.time.LocalDate;
import java.util.List;

public interface JavitasService {

    List<Javitas> getJavitasList(Lepcso lepcso) throws Exception;

    List<Javitas> getJavitasList() throws Exception;

    void saveJavitas(Javitas javitas) throws Exception;

    List<Javitas> getJavitasInterval(Lepcso lepcso, LocalDate startDate, LocalDate endDate);

    List<Javitas> getIdoszakosJavitas(LocalDate startDate, LocalDate endDate);
}
