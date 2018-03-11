package ch.bbbaden.m335.rezepteverwaltung.services;

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
import ch.bbbaden.m335.rezepteverwaltung.objects.User;
import ch.bbbaden.m335.rezepteverwaltung.tools.*;

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
                DataHolder.getInstance().setRezepteListe(returnList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return DataHolder.getInstance().getRezepteListe();
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
                DataHolder.getInstance().setRezepteListe(returnList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return DataHolder.getInstance().getRezepteListe();
    }

    public void addRezeptToFirebase(Rezept rezept) {
        if (rezept.isRezeptPublic()) {
            mDatabase.child(MainActivity.context.getResources().getString(R.string.dbpublic)).child(rezept.getRezeptAuthor()).child(rezept.getRezeptId()).setValue(rezept);
        } else {
            mDatabase.child(MainActivity.context.getResources().getString(R.string.dbprivate)).child(rezept.getRezeptAuthor()).child(rezept.getRezeptId()).setValue(rezept); //TODO User Name in DB name einfügen
        }
    }

    public List<User> getAllUsers() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.println(getClass().toString()+"getAllUsers");
        List<User> returnList;
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase Users");
                List<User> userList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user =noteDataSnapshot.getValue(User.class);
                    System.out.println("userMail "+user.getUserEmail());
                    userList.add(user);
                    System.out.println("1 returnList().size() =" + userList.size());
                }
                DataHolder.getInstance().setUserListe(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        returnList = DataHolder.getInstance().getUserListe();
        //System.out.println("2 returnList.size() 2=" + returnList.size());
        return returnList;
    }

    public void addUserToFirebase(User user, String userId) {
        mDatabase.child(MainActivity.context.getResources().getString(R.string.users)).child(userId).setValue(user);
    }

    public User getUserById(String userId) {
        final List<User> user = new ArrayList<>();
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            User userI;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.add(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return user.get(0);
    }
}
