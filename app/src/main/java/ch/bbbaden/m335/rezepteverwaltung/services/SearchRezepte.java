package ch.bbbaden.m335.rezepteverwaltung.services;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.activities.SearchRezepteActivity;
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
            alleRezepte = DatabaseConector.getRezepte();
        } catch (Exception e) {
            System.out.println("Datenbank fehler");
        }
        namenRezepte = new ArrayList<>();
        authorRezepte = new ArrayList<>();
        zutatenRezepte = new ArrayList<>();
//        try {
        if (!queryName.equals("") || queryName.toLowerCase().equals("name")) {
            for (int i = 0; i < alleRezepte.size(); i++) {
                if (alleRezepte.get(i).getRezeptName().toLowerCase().contains(queryName.toLowerCase())) {
                    namenRezepte.add(alleRezepte.get(i));
                }
            }
        } else {
            namenRezepte = alleRezepte;
        }

        if (!queryAuthor.equals("") || queryAuthor.toLowerCase().equals("author")) {
            for (int i = 0; i < namenRezepte.size(); i++) {
                if (namenRezepte.get(i).getRezeptAuthor().toLowerCase().contains(queryAuthor.toLowerCase())) {
                    authorRezepte.add(namenRezepte.get(i));
                }
            }
        } else {
            authorRezepte = namenRezepte;
        }
        System.out.println("query size " + queryZutaten.size());
        for (int i = 0; i < queryZutaten.size(); i++) {
            System.out.println("query " + queryZutaten.get(i));
        }

        if (queryZutaten.size() > 0) {
            if (!queryZutaten.get(0).equals("")) {
                System.out.println("called");
                for (int i = 0; i < authorRezepte.size(); i++) {
                    System.out.println("+called2 " + i);
                    if (authorRezepte.get(i).getRezeptZutaten() != null) {
                        System.out.println("called3");
                        for (int j = 0; j < authorRezepte.get(i).getRezeptZutaten().size(); j++) {
                            System.out.println("called4 " + j);
                            for (int l = 0; l < queryZutaten.size(); l++) {
                                if (!queryZutaten.get(l).equals("")) {
                                    System.out.println("Zutaten: " + authorRezepte.get(i).getRezeptZutaten().get(j) + " " + queryZutaten.get(l));
                                    if (authorRezepte.get(i).getRezeptZutaten().get(j).toLowerCase().contains(queryZutaten.get(l).toLowerCase())) {
                                        zutatenRezepte.add(authorRezepte.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                zutatenRezepte = authorRezepte;
            }
        } else {
            System.out.println("Zutaten else");
            zutatenRezepte = authorRezepte;
        }

        authorRezepte = null;
        namenRezepte = null;
//        } catch (Exception e) {
//            System.out.println("Suchfehler");
//
//        }

        return zutatenRezepte;
    }
}
