package com.petersoft.mgl.service;

import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Lepcso;

import java.util.List;

public interface HibaService {

    List<Hiba> getHibaList(Lepcso lepcso) throws Exception;

    List<Hiba> getHibaList() throws Exception;

    void saveHiba(Hiba hiba) throws Exception;
}
