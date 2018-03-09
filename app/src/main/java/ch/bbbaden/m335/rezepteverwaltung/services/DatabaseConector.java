package ch.bbbaden.m335.rezepteverwaltung.services;

import com.google.firebase.auth.FirebaseAuth;

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

    public static Rezept getRezepteById(String id) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        return db.rezeptDAO().loadAllByIds(Integer.parseInt(id));
    }

    public static List<Rezept> getRezepteCombined() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        List<Rezept> rezepte = db.rezeptDAO().getAll();
        System.out.println("RezepteListe Grösse lokal: " + rezepte.size());
        List<Rezept> fbRezepte = new FirebaseConector().downloadAllRezepte();
        for (int i = 0; i < fbRezepte.size(); i++) {
            if (getRezepteById(fbRezepte.get(i).getRezeptId()) == null) {
                rezepte.add(fbRezepte.get(i));
            }
        }

        return rezepte;
    }

    static public String generateId(Rezept rezept) {
        String returnId;
        String userId = FirebaseAuth.getInstance().getUid();

        if (rezept.isRezeptOnline()) {
            if (rezept.isRezeptPublic()) {
                returnId = "10";
            } else {
                returnId = "20";
            }
        } else {
            returnId = "30";
        }

        returnId += "" + String.valueOf(rezept.getRezeptAuthor()).length() + userId + DatabaseConector.getRezepteCombined().size();

        return returnId;
    }
}