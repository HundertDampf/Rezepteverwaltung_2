package ch.bbbaden.m335.rezepteverwaltung.services;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;

/**
 * Created by Noah on 07.03.2018.
 */

public class DatabaseConector {

    public static void addRezepte(Rezept[] rezepte) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.rezeptDAO().insertAll(rezepte);
        System.out.println("Rezept added, DBConector");
    }

    public static void addRezept(Rezept rezept) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.rezeptDAO().insertAll(rezept);
        System.out.println("Rezept added, DBConector");
    }

    public static List<Rezept> getRezepte() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        List<Rezept> rezepts = db.rezeptDAO().getAllRezepte();
        System.out.println("RezepteListe Grösse: " + rezepts.size());
        return rezepts;
    }

    public static void deleteRezepte() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.rezeptDAO().deleteAll();
    }

    public static Rezept getRezepteById(String id) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        return db.rezeptDAO().loadAllRezepteByIds(Integer.parseInt(id));
    }

    static public String generateId(Rezept rezept) {
        String returnId;
        String userId = getUserByMail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).getUserShortId() + "";

        if (rezept.isRezeptOnline()) {
            if (rezept.isRezeptPublic()) {
                returnId = "10";
            } else {
                returnId = "20";
            }
        } else {
            returnId = "30";
        }

        returnId += "" + String.valueOf(userId).length() + userId + DatabaseConector.getRezepte().size();
        System.out.println("returnId " + returnId);
        return returnId;
    }

    public static User getUserByMail(String userMail) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));

        return db.userDAO().loadUserByMail(userMail);
    }

    public static User addUser(User user) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.userDAO().insertAll(user);
        System.out.println("User added, DBConector");
        return user;
    }

    public static void addRezepteFromFirebase(List<Rezept> rezepteFromFirebase) {
        List<Rezept> rezepteFromRoom = getRezepte();
        List<Rezept> returnList = new ArrayList<>();

        System.out.println("rezeptefromFB size " + rezepteFromFirebase.size());
        System.out.println("rezeptefromroom size " + rezepteFromRoom.size());


        for (int i = 0; i < rezepteFromFirebase.size(); i++) {
            boolean isInRoomDb = false;
            System.out.println("FB loop " + i);
            for (int j = 0; j < rezepteFromRoom.size(); j++) {

                System.out.println("Room loop " + i + "  " + j);
                System.out.println(rezepteFromFirebase.get(i).getRezeptId());
                System.out.println(rezepteFromRoom.get(j).getRezeptId());

                if (rezepteFromFirebase.get(i).getRezeptId().equals(rezepteFromRoom.get(j).getRezeptId())) {
                    isInRoomDb = true;
                }
                System.out.println(isInRoomDb);
            }

            if (!isInRoomDb) {
                returnList.add(rezepteFromFirebase.get(i));
            }
        }
        Rezept[] rezepteArray = new Rezept[returnList.size()];
        for (int i = 0; i < returnList.size(); i++) {
            rezepteArray[i] = returnList.get(i);
        }
        System.out.println("rezeptearray length " + rezepteArray.length);
        System.out.println(rezepteArray.toString());

        if (rezepteArray.length > 0) {
            addRezepte(rezepteArray);
        }
    }

    public static void deleteUser(User user) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.userDAO().delete(user);
    }
}