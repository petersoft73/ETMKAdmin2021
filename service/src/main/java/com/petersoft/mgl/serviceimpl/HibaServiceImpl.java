package com.petersoft.mgl.serviceimpl;

import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.HibaService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HibaServiceImpl implements HibaService {

    public HibaServiceImpl() {
    }

    @Override
    public List<Hiba> getHibaList(Lepcso lepcso) {
        return DBConnector.getHibaList().stream()
                .filter(e -> !e.getLeiras().equals(""))
                .filter(hiba -> hiba.getLepcso().getLepcsoSzama() == lepcso.getLepcsoSzama())
                .sorted(Comparator.comparing(Hiba::getDatum).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Hiba> getHibaList() {
        return DBConnector.getHibaList().stream()
                .filter(e -> !e.getLeiras().equals(""))
                .collect(Collectors.toList());
    }

    @Override
    public void saveHiba(Hiba hiba) {
        DBConnector.saveHiba(hiba);
    }
}
