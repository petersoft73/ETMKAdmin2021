package com.petersoft.mgl.model;

import com.petersoft.mgl.connection.MainFrameQuery;

import java.util.List;

public class Database {

    private static List<Hiba> hibaList;
    private static List<Javitas> javitasList;
    private static List<Location> locationList;
    private static List<Tipus> tipusList;

    public Database() {
        initDBLists();
    }


    public static void saveLepcso(Lepcso lepcso) {
        MainFrameQuery.saveLepcso(lepcso);
    }

    public static List<Lepcso> getLepcsoList() {
        return MainFrameQuery.getLepcsoList();
    }


    public static void addHiba(Hiba hiba) {
        hibaList.add(hiba);
    }

    public static void saveHiba(Hiba hiba) {
        MainFrameQuery.saveHiba(hiba);
    }

    public static List<Hiba> getHibaList() {
        hibaList = MainFrameQuery.getHibaList();
        return hibaList;
    }

    public static void rollback() {
        MainFrameQuery.rollback();
    }

    public static void addJavitas(Javitas javitas) {
        MainFrameQuery.saveJavitas(javitas);
    }

    public static List<Javitas> getJavitasList() {
        javitasList = MainFrameQuery.getJavitasList();
        return javitasList;
    }

    public static void deleteLepcso(Lepcso lepcso) {
        MainFrameQuery.deleteLepcso(lepcso);
    }


    public static List<Location> getLocationList() {
        locationList = MainFrameQuery.getLocationList();
        return locationList;
    }



    public static List<Tipus> getTipusList() {
        tipusList = MainFrameQuery.getTipusList();
        return tipusList;
    }



    private void initDBLists() {
        tipusList = MainFrameQuery.getTipusList();
        locationList = MainFrameQuery.getLocationList();
        hibaList = MainFrameQuery.getHibaList();
        javitasList = MainFrameQuery.getJavitasList();
    }

}
