package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;
import ch.bbbaden.m335.rezepteverwaltung.tools.Toaster;

public class RezepteListActivity extends AppCompatActivity {
    List<Rezept> rezepte;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezepte_list);

        rezepte = DataHolder.getInstance().getRezepteListe();
        listView = findViewById(R.id.listView);
        if (rezepte != null) {
            if (rezepte.size() == 1) {
                DataHolder.getInstance().setRezept(rezepte.get(0));
                startActivity(new Intent(getApplicationContext(), RezeptActivity.class));
            } else if (rezepte.size() > 1) {

                String[] listItems = new String[rezepte.size()];

                for (int i = 0; i < rezepte.size(); i++) {
                    Rezept recipe = rezepte.get(i);
                    listItems[i] = recipe.getRezeptName();
                }

                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Rezept selectedRecipe = rezepte.get(position);
                        DataHolder.getInstance().setRezept(selectedRecipe);
                        startActivity(new Intent(getApplicationContext(), RezeptActivity.class));
                    }

                });
            }
        } else {
            new Toaster(getWindow().getDecorView().findViewById(android.R.id.content), "Keine Rezepte gefunden", -2);
        }
    }

//    private static List<Rezept> getRezepte(final AppDatabase db) {
//        List<Rezept> rezepts = db.rezeptDAO().getAll();
//        System.out.println("RezepteListe Grösse: " + rezepts.size());
//        return rezepts;
//    }
}
