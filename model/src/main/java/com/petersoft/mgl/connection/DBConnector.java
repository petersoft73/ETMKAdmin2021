package com.petersoft.mgl.connection;

import com.petersoft.mgl.dolgozo.Dolgozo;
import com.petersoft.mgl.dolgozo.DolgozoNapiRekord;
import com.petersoft.mgl.model.Alkatresz;
import com.petersoft.mgl.model.*;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DBConnector extends AbstractQuery {

    public DBConnector() {
    }

    public static List<Lepcso> getLepcsoList() {
        open();
        TypedQuery<Lepcso> query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT l FROM Lepcso l", Lepcso.class);
        return query.getResultList();
    }

    public static List<Tipus> getTipusList() {
        open();
        TypedQuery<Tipus> query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT t FROM Tipus t", Tipus.class);
        return query.getResultList();
    }

    public static List<Location> getLocationList() {
        open();
        TypedQuery<Location> query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT l FROM Location l", Location.class);
        return query.getResultList();
    }

    public static void saveTipus(Tipus tipus) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(tipus);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static void saveLocation(Location location) {

        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(location);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static void saveLepcso(Lepcso lepcso) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().merge(lepcso);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static List<ErintesVedelem> getErVedList() {
        open();
        TypedQuery<ErintesVedelem> query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT ev FROM ErintesVedelem ev", ErintesVedelem.class);
        return query.getResultList();
    }

    public static void saveErintesVedelem(ErintesVedelem e) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(e);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static List<Hiba> getHibaList() {
        open();
        TypedQuery<Hiba> query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT hiba FROM Hiba hiba", Hiba.class);
        return query.getResultList();
    }

    public static List<Javitas> getJavitasList() {
        open();
        TypedQuery<Javitas> query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT jav FROM Javitas jav", Javitas.class);
        return query.getResultList();
    }

    public static void saveHiba(Hiba hiba) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(hiba);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static void saveJavitas(Javitas javitas) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(javitas);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static void shutdown() {
        shuttingdown();
    }

    public static void deleteLepcso(Lepcso lepcso) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().remove(lepcso);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }


    public static void rollback() {
        EntityManagerHandler.INSTANCE.getEntityTransaction().rollback();
    }

    public static List<KarbantartasTipus> getKarbantartasTipusList() {
        open();
        return EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery("SELECT karbTipus FROM KarbantartasTipus karbTipus",
                        KarbantartasTipus.class)
                .getResultList();
    }

    public static List getKarbantartasList(Lepcso lepcso) {
        open();
        String sql = "SELECT karb FROM Karbantartas karb " +
                "WHERE lepcsoSzama = :lepcsoId " +
                "ORDER BY karbTipusId";
        int lepcsoId = lepcso.getLepcsoSzama();
        Query query = EntityManagerHandler.INSTANCE.getEntityManager().createQuery(sql);
        query.setParameter("lepcsoId", lepcsoId);
        return query.getResultList();
    }

    public static List<Karbantartas> getAllKarbantartas() {
        open();
        Query query = EntityManagerHandler.INSTANCE
                .getEntityManager().createQuery("SELECT karb FROM Karbantartas karb WHERE lepcsoSzama >=0");
        return query.getResultList();
    }

    public static void addKarbantartas(Karbantartas k) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(k);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static void updateKarbantartas(Karbantartas k) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(k);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static List<Lepcso> getHianyzoKarbList() {
        open();
        String sql = "SELECT DISTINCT k.lepcso FROM Karbantartas k WHERE k.elvegezve = false";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager().createQuery(sql);
        List<Lepcso> hianyzoList = query.getResultList();
        hianyzoList.sort(Comparator.comparing(Lepcso::getLepcsoSzama));
        return hianyzoList;
    }

    public static void deleteKarbantartas(Karbantartas k) {
        open();
        if (k != null) {
            EntityManagerHandler.INSTANCE.getEntityManager().remove(k);
            EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
        }
    }

    public static List<Status> getStatusList() {
        open();
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery("SELECT status FROM Status status", Status.class);

        return query.getResultList();
    }

    public static List<Karbantartas> getKarbantartasByDate(LocalDate date) {
        open();
        String sql = "SELECT k FROM Karbantartas k WHERE karbantartasDatum =:datum " +
                "ORDER BY lepcsoSzama, karbTipusId";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery(sql, Karbantartas.class);

        return query.setParameter("datum", date).getResultList();
    }

    public static List<Javitas> getJavitasInterval(int lepcsoSzama,
                                                   LocalDate startDate,
                                                   LocalDate endDate) {
        open();
        String sql = "SELECT j FROM Javitas j WHERE lepcso_lepcsoSzama =:lepcsoSzama" +
                " AND (javitasKelte BETWEEN :startDate AND :endDate)";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager().createQuery(sql, Javitas.class);
        query.setParameter("lepcsoSzama", lepcsoSzama);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

    public static List<Karbantartas> getKarbantartasInterval(int lepcsoSzama,
                                                             LocalDate startDate,
                                                             LocalDate endDate) {
        open();
        String sql = "SELECT k FROM Karbantartas k WHERE lepcsoSzama =:lepcsoSzama " +
                "AND (karbantartasDatum BETWEEN :startDate AND :endDate) ORDER BY karbTipusId";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery(sql, Karbantartas.class);
        query.setParameter("lepcsoSzama", lepcsoSzama);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

    public static List<Karbantartas> getReportHianyzoKarbList(LocalDateTime startDate, LocalDateTime endDate) {
        open();
        String sql = "SELECT k FROM Karbantartas k " +
                "WHERE elvegezve = 'false' " +
                "AND updateDateTime BETWEEN :startDate AND :endDate " +
                "ORDER BY lepcsoSzama, karbantartasTipus";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery(sql, Karbantartas.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

    public static List<Alkatresz> getAlkatreszList() {
        open();
        String sql = "SELECT a FROM Alkatresz a";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery(sql, Alkatresz.class);

        return query.getResultList();
    }

    public static List<Dolgozo> getAllDolgozo(Integer[] muszakSzama) {
        open();
        int muszak1 = muszakSzama[0];
        int muszak2 = muszakSzama[1];
        String sql = "SELECT d FROM Dolgozo d WHERE " +
                "(turSzama =:muszak1 OR turszama =:muszak2 OR turSzama = 5) " +
                "AND aktiv = 1 ORDER BY turSzama, nev";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery(sql, Dolgozo.class);
        query.setParameter("muszak1", muszak1);
        query.setParameter("muszak2", muszak2);
        return query.getResultList();
    }

    public static void saveDolgozoNapiRekord(DolgozoNapiRekord dnr) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().persist(dnr);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }

    public static List<DolgozoNapiRekord> getDolgozoNapiRekord(LocalDate date) {
        open();
        String sql = "SELECT dnr FROM DolgozoNapiRekord dnr WHERE munkanap =:date";
        Query query = EntityManagerHandler.INSTANCE.getEntityManager()
                .createQuery(sql, DolgozoNapiRekord.class);
        query.setParameter("date", date);

        return query.getResultList();
    }

    public static void deleteNapiRekord(DolgozoNapiRekord napiRekord) {
        open();
        EntityManagerHandler.INSTANCE.getEntityManager().remove(napiRekord);
        EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
    }
}