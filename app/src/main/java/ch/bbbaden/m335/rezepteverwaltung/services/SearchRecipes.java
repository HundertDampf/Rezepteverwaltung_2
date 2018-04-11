package ch.bbbaden.m335.rezepteverwaltung.services;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;

/**
 * Created by Noah on 02.03.2018.
 */
public class SearchRecipes {
    List<Recipe> allAccesableRecipes;
    List<Recipe> resultsByName;
    List<Recipe> resultsByAuthor;
    List<Recipe> resultsByIngredients;

    public List<Recipe> doSearch(String queryName, String queryAuthor, List<String> queryZutaten) {
        try {
            allAccesableRecipes = DatabaseConector.getRezepte();
        } catch (Exception e) {
            System.out.println("Datenbank fehler");
        }

        resultsByName = new ArrayList<>();
        resultsByAuthor = new ArrayList<>();
        resultsByIngredients = new ArrayList<>();

        try {
            //Suche nach Namen
            if (!queryName.equals("") || queryName.toLowerCase().equals("name")) {
                for (int i = 0; i < allAccesableRecipes.size(); i++) {
                    if (allAccesableRecipes.get(i).getRecipeName().toLowerCase().contains(queryName.toLowerCase())) {
                        resultsByName.add(allAccesableRecipes.get(i));
                    }
                }
            } else {
                resultsByName = allAccesableRecipes;
            }

            //Suche nach Author
            if (!queryAuthor.equals("") || queryAuthor.toLowerCase().equals("author")) {
                for (int i = 0; i < resultsByName.size(); i++) {
                    if (resultsByName.get(i).getRecipeAuthor().toLowerCase().contains(queryAuthor.toLowerCase())) {
                        resultsByAuthor.add(resultsByName.get(i));
                    }
                }
            } else {
                resultsByAuthor = resultsByName;
            }

            //Suche nach Zutaten
            System.out.println("query size " + queryZutaten.size());
            for (int i = 0; i < queryZutaten.size(); i++) {
                System.out.println("query " + queryZutaten.get(i));
            }

            if (queryZutaten.size() > 0 && !queryZutaten.get(0).equals("")) {              //Testet ob nach Zutaten gesucht wird
                System.out.println("queryZuaten ist nicht leer");
                for (int i = 0; i < resultsByAuthor.size(); i++) {
                    System.out.println("Authorenresultate Loop Durchlauf:" + i);
                    if (resultsByAuthor.get(i).getRecipeIngredientsAsList() != null) {                //Testet ob Recipe Zutaten beinhaltet
                        System.out.println("Zutaten List ist nicht null");
                        for (int j = 0; j < resultsByAuthor.get(i).getRecipeIngredientsAsList().size(); j++) {
                            System.out.println("Zutatenloop von Authorenresultaten Durchlauf:" + j);
                            for (int l = 0; l < queryZutaten.size(); l++) {
                                System.out.println("Zutatenquery Loop Durchlauf:" + l);
                                if (!queryZutaten.get(l).equals("")) {                         //Testet für leere Zutaten Einträge
                                    System.out.println("Zutaten: " + resultsByAuthor.get(i).getRecipeIngredientsAsList().get(j) + " " + queryZutaten.get(l));
                                    if (resultsByAuthor.get(i).getRecipeIngredientsAsList().get(j).toLowerCase().contains(queryZutaten.get(l).toLowerCase())) {
                                        resultsByIngredients.add(resultsByAuthor.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Keine Rezepte gefunden die auf die Zutatensuche passen");
                resultsByIngredients = resultsByAuthor;
            }
            resultsByAuthor = null;
            resultsByName = null;
        } catch (Exception e) {
            System.out.println("Suchfehler");
        }

        return resultsByIngredients;
    }
}
