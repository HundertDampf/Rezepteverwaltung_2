package ch.bbbaden.m335.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

public class RezeptActivity extends AppCompatActivity {
    Rezept rezeptToShow;

    ListView listZutaten;
    TextView viewDauer;
    TextView viewZubereitung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept);
        //TODO ersetzen der ListView
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rezeptToShow = DataHolder.getInstance().getRezept();
        viewDauer = findViewById(R.id.viewRezeptDauer);
        viewZubereitung = findViewById(R.id.viewRezeptZubereitung);
        listZutaten = findViewById(R.id.listRezeptZutaten);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShareRezepteActivity.class));
            }
        });

        if (rezeptToShow.getRezeptName() != null) {
            setTitle(rezeptToShow.getRezeptName());
        } else {
            setTitle("undefined");
        }
        String[] zutatenListItems;
        if (rezeptToShow.getRezeptZutatenString() != null) {
            System.out.println("Size rezeptToShow.getRezeptZutaten====================== " + rezeptToShow.getRezeptZutaten().size());
            zutatenListItems= new String[rezeptToShow.getRezeptZutaten().size()];

            for (int i = 0; i < rezeptToShow.getRezeptZutaten().size(); i++) {
                System.out.println("getZutaten loop " + i);
                zutatenListItems[i] = rezeptToShow.getRezeptZutaten().get(i);
                System.out.println(zutatenListItems[i]);
            }
        }else{
            zutatenListItems = new String[1];
            zutatenListItems[0]="undefined";
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, zutatenListItems);
        listZutaten.setAdapter(adapter);

        if(rezeptToShow.getRezeptDauer()!=null) {
            viewDauer.setText(rezeptToShow.getRezeptDauer());
        }else{
            viewDauer.setText("undefined");
        }

        if(rezeptToShow.getRezeptZubereitung()!=null) {
            viewZubereitung.setText(rezeptToShow.getRezeptZubereitung());
        }else{
            viewZubereitung.setText("undefined");
        }

        new Toaster(getApplicationContext(), rezeptToShow.getRezeptId() + " rezeptID", 1);

    }
}
