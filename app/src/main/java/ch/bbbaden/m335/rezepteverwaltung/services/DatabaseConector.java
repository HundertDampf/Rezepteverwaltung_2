package ch.bbbaden.m335.rezepteverwaltung.services;

import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;

/**
 * Created by Noah on 07.03.2018.
 */

public class DatabaseConector {

    public static Rezept addRezept(Rezept rezept) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.rezeptDAO().insertAll(rezept);
        System.out.println("Rezept added, DBConector");
        return rezept;
    }

    public static List<Rezept> getRezepte() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        List<Rezept> rezepts = db.rezeptDAO().getAll();
        System.out.println("RezepteListe Grösse: " + rezepts.size());
        return rezepts;
    }

    public static void deleteRezepte() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.rezeptDAO().deleteAll();
    }
}
