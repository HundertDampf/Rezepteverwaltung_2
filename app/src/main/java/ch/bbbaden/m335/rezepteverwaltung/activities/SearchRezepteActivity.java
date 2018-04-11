package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.services.SearchRezepte;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;
import ch.bbbaden.m335.rezepteverwaltung.tools.VariousMethods;

public class SearchRezepteActivity extends AppCompatActivity {

    Button btnSearch;
    EditText editName;
    EditText editAuthor;
    EditText editZutaten;

    String queryName;
    String queryAuthor;
    List<String> queryZutaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rezepte);

        editName = findViewById(R.id.editSuchName);
        editAuthor = findViewById(R.id.editSuchAuthor);
        editZutaten = findViewById(R.id.editSuchZutaten);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryName = editName.getText().toString();
                queryAuthor = editAuthor.getText().toString();

                queryZutaten = new ArrayList<>();
                System.out.println("queryzutatent " + editAuthor.getText().toString());
                String[] queryZutatenArray = editZutaten.getText().toString().split(",");
                System.out.println("lenght queryZutatenArray " + queryZutatenArray.length);

                for (int i = 0; i < queryZutatenArray.length; i++) {
                    System.out.println("array " + queryZutatenArray[i]);
                    queryZutaten.add(queryZutatenArray[i].trim());
                    System.out.println(queryZutaten.get(i));
                }

                List<Rezept> rezepte = new SearchRezepte().doSearch(queryName, queryAuthor, queryZutaten);
                queryZutaten = null;
                for (int i = 0; i < rezepte.size(); i++) {
                    System.out.println(rezepte.get(i).getRezeptName());
                    System.out.println(rezepte.get(i).getRezeptZubereitung());
                }
                if (rezepte != null) {
                    if (rezepte.size() == 1) {
                        DataHolder.getInstance().setRezept(rezepte.get(0));
                        startActivity(new Intent(getApplicationContext(), RezeptActivity.class));
                    } else if (rezepte.size() > 1) {
                        DataHolder.getInstance().setRezepteListe(rezepte);
                        new VariousMethods().goToNewActivity(RezepteListActivity.class, SearchRezepteActivity.this);
                    } else {
                        new Toaster(getWindow().getDecorView().findViewById(android.R.id.content), "Keine der Suche entsprechenden Rezepte gefunden", -2);

                    }
                } else {
                    new Toaster(getWindow().getDecorView().findViewById(android.R.id.content), "Keine Rezepte in der Datebank gefunden", -2);
                }
            }
        });
    }
}
