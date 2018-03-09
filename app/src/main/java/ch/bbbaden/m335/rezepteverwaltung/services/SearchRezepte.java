package ch.bbbaden.m335.rezepteverwaltung.services;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;

/**
 * Created by Noah on 02.03.2018.
 */
public class SearchRezepte {
    List<Rezept> alleRezepte;
    List<Rezept> namenRezepte;
    List<Rezept> authorRezepte;
    List<Rezept> zutatenRezepte;

    public List<Rezept> doSearch(String queryName, String queryAuthor, List<String> queryZutaten) {
        try {
            alleRezepte = DatabaseConector.getRezepteCombined();
        } catch (Exception e) {
            System.out.println("Datenbank fehler");
        }
        namenRezepte = new ArrayList<>();
        authorRezepte = new ArrayList<>();
        zutatenRezepte = new ArrayList<>();
        try {
            if (queryName != "") {
                for (int i = 0; i < alleRezepte.size(); i++) {
                    if (alleRezepte.get(i).getRezeptName().contains(queryName)) {
                        namenRezepte.add(alleRezepte.get(i));
                    }
                }
            } else {
                namenRezepte = alleRezepte;
            }

            if (queryAuthor != "") {
                for (int i = 0; i < namenRezepte.size(); i++) {
                    if (namenRezepte.get(i).getRezeptAuthor().contains(queryAuthor)) {
                        authorRezepte.add(namenRezepte.get(i));
                    }
                }
            } else {
                authorRezepte = namenRezepte;
            }

            if (queryZutaten.size() > 0) {
//            for (int i = 0; i > authorRezepte.size(); i++) {
//                for (int j = 0; i > authorRezepte.get(i).getRezeptZutaten[1].size(); j++) {
//                    for (int l = 0; l > queryZutaten.size(); l++) {
//                        if (authorRezepte.get(i).getRezeptZutaten[1].get(j) == queryZutaten.get(l)) {
//                            zutatenRezepte.add(authorRezepte.get(i));
//                        }
//                    }
//                }
//            }
            } else {
                zutatenRezepte = authorRezepte;
            }


        } catch (Exception e) {
            System.out.println("Suchfehler");

        }
        return zutatenRezepte;
    }

//    private static List<Rezept> getAlleRezepte(final AppDatabase db) {
//        return db.rezeptDAO().getAll();
//    }
}
