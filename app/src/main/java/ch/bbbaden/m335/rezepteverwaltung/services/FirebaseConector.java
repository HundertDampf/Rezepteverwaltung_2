package ch.bbbaden.m335.rezepteverwaltung.services;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.R;
import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;

/**
 * Created by Noah on 02.03.2018.
 */

public class FirebaseConector {
    DatabaseReference mDatabase;

    public FirebaseConector() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.println("FirebaseConector Constructor--------------------");
    }

    public List<Rezept> downloadAllRezepte() {
        final List<Rezept> returnList = new ArrayList<>();
        mDatabase.child("publicRezepter").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase");

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    returnList.add(noteDataSnapshot.getValue(Rezept.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return returnList;
    }

    public List<Rezept> downloadAllFromUser(int userId) {
        final List<Rezept> returnList = new ArrayList<>();
        mDatabase.child("publicRezepter").child("" + userId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase");

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    returnList.add(noteDataSnapshot.getValue(Rezept.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return returnList;
    }

    public void addRezeptToFirebase(Rezept rezept) {
        if (rezept.isRezeptPublic()) {
            mDatabase.child(MainActivity.context.getResources().getString(R.string.dbpublic)).child(rezept.getRezeptAuthor()).child(rezept.getRezeptId()).setValue(rezept);
        } else {
            mDatabase.child(MainActivity.context.getResources().getString(R.string.dbprivate)).child(rezept.getRezeptAuthor()).child(rezept.getRezeptId()).setValue(rezept); //TODO User Name in DB name einfügen
        }
    }
}
