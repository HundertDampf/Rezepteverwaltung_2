package ch.bbbaden.m335.rezepteverwaltung.services;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;

/**
 * Created by Noah on 07.03.2018.
 */

public class DatabaseConector {

    public static void addRezepte(List<Recipe> rezepte) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        Recipe[] rezepteArray = new Recipe[rezepte.size()];
        for (int i = 0; i < rezepte.size(); i++) {
            rezepteArray[i] = rezepte.get(i);
        }
        db.recipeDAO().insertAll(rezepteArray);
        System.out.println(rezepteArray.length + " Rezepte added, DBConector");
    }

    public static void addRezept(Recipe recipe) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.recipeDAO().insertAll(recipe);
        System.out.println("Recipe added, DBConector");
    }

    public static List<Recipe> getRezepte() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        List<Recipe> recipes = db.recipeDAO().getAllRecipes();
        System.out.println(recipes.size() + " Reazepte fetched, DBConnector");
        return recipes;
    }

    public static void deleteRezepte() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.recipeDAO().deleteAll();
        System.out.println("Recipe deleted, DBConnector");
    }

    public static Recipe getRezepteById(String id) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        System.out.println("Recipe fetched, DBConnector");
        return db.recipeDAO().loadAllRecipesByIds(Integer.parseInt(id));
    }

    public static List<Recipe> getRezepteByAuthor(String author) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        System.out.println("Recipe fetched, DBConnector");
        return db.recipeDAO().findRecipeByAuthor(author);
    }

    public static User getUserByMail(String userMail) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        System.out.println("User fetched, DBConnector");
        return db.userDAO().loadUserByMail(userMail);
    }

    public static void addUser(User user) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.userDAO().insertAll(user);
        System.out.println("User added, DBConector");
    }
    public static void addUser(List<User> users) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        User[] usersArray = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            usersArray[i] = users.get(i);
        }
        db.userDAO().insertAll(usersArray);
        System.out.println(usersArray.length + " Rezepte added, DBConector");
    }

    public static List<User> getUsers() {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        List<User> users = db.userDAO().getAllUser();
        System.out.println(users.size() + " User fetched, DBConnector");
        return users;
    }

    public static void deleteUser(User user) {
        final AppDatabase db = (AppDatabase.getAppDatabase(MainActivity.context));
        db.userDAO().delete(user);
        System.out.println("User deleted, DBConnector");
    }

    public static void addRezepteFromFirebase(List<Recipe> rezepteFromFirebase) {
        List<Recipe> rezepteFromRoom = getRezepte();
        List<Recipe> returnList = new ArrayList<>();

        System.out.println("rezeptefromFB size " + rezepteFromFirebase.size());
        System.out.println("rezeptefromroom size " + rezepteFromRoom.size());

        for (int i = 0; i < rezepteFromFirebase.size(); i++) {
            boolean isInRoomDb = false;
            System.out.println("FB loop " + i);
            for (int j = 0; j < rezepteFromRoom.size(); j++) {
                if (rezepteFromFirebase.get(i).getRecipeId().equals(rezepteFromRoom.get(j).getRecipeId())) {
                    isInRoomDb = true;
                }
            }

            if (!isInRoomDb) {
                returnList.add(rezepteFromFirebase.get(i));
            }
        }

        if (returnList.size() > 0) {
            addRezepte(returnList);
        }
    }

    public static void addUserFromFirebse(List<User> userFromFirebase) {
        List<User> returnList = new ArrayList<>();
        List<User> userFromRoomDB = getUsers();
        if (userFromFirebase != null && userFromRoomDB != null) {
            for (int i = 0; i < userFromFirebase.size(); i++) {
                if(userFromFirebase.get(i).getUserLongId().contains("admin")){
                    userFromFirebase.get(i).setUserEmail("nahanahahnahahnah"+userFromFirebase.get(i).getUserLongId());
                    userFromFirebase.get(i).setUserName(userFromFirebase.get(i).getUserLongId());
                }
                boolean isInRoomDb = false;
                System.out.println("FB loop " + i);
                for (int j = 0; j < userFromRoomDB.size(); j++) {
                    System.out.println(userFromFirebase.get(i).getUserLongId());
                    System.out.println(userFromRoomDB.get(j).getUserLongId());
                    if (userFromFirebase.get(i).getUserLongId().equals(userFromRoomDB.get(j).getUserLongId())) {
                        isInRoomDb = true;
                    }
                }

                if (!isInRoomDb) {
                    returnList.add(userFromFirebase.get(i));
                }
            }

            if (returnList.size() > 0) {
                addUser(returnList);
            }
        }else if(userFromRoomDB==null){
            addUser(userFromFirebase);
        }
    }
}