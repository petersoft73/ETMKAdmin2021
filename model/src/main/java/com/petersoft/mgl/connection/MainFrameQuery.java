package com.petersoft.mgl.connection;

import com.petersoft.mgl.model.*;

import javax.persistence.TypedQuery;
import java.util.List;

public class MainFrameQuery extends AbstractQuery {

    public MainFrameQuery() {

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
}
