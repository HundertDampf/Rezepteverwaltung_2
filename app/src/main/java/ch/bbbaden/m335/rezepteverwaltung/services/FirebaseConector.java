package ch.bbbaden.m335.rezepteverwaltung.services;

import com.google.firebase.auth.FirebaseAuth;
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
import ch.bbbaden.m335.rezepteverwaltung.tools.DataHolder;

/**
 * Created by Noah on 02.03.2018.
 */

public class FirebaseConector {
    DatabaseReference mDatabase;

    public FirebaseConector() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.println("FirebaseConector Constructor--------------------");
    }

    public List<Rezept> downloadAllRezepte(String userId) {
        final List<Rezept> returnList = new ArrayList<>();
        final List<Rezept> returnPrivateList = new ArrayList<>();


        mDatabase.child("publicRezepte").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase rezepte public");

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    returnList.add(noteDataSnapshot.getValue(Rezept.class));
                }
//                DatabaseConector.addRezepteFromFirebase(returnList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child("privateRezepte").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase rezepte private");

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println("rezept id "+noteDataSnapshot.getValue(Rezept.class).getRezeptId());
                    returnPrivateList.add(noteDataSnapshot.getValue(Rezept.class));
                }
                DatabaseConector.addRezepteFromFirebase(returnPrivateList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return DataHolder.getInstance().getRezepteListe();
    }

    public List<Rezept> downloadAllFromUser(int userId) {
        final List<Rezept> returnList = new ArrayList<>();
        mDatabase.child("publicRezepter").child("" + userId).addListenerForSingleValueEvent(new ValueEventListener() {

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
            mDatabase.child(MainActivity.context.getResources().getString(R.string.dbpublic)).child(Long.toString(DatabaseConector.getUserByMail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).getUserShortId())).child(rezept.getRezeptId()).setValue(rezept);
        } else {
            mDatabase.child(MainActivity.context.getResources().getString(R.string.dbprivate)).child(Long.toString(DatabaseConector.getUserByMail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).getUserShortId())).child(rezept.getRezeptId()).setValue(rezept); //TODO User Name in DB name einfügen
        }
    }

    public void getAllUsers() {
        System.out.println(getClass().toString() + "getAllUsers");

        mDatabase.child(MainActivity.context.getResources().getString(R.string.users)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase Users");
                List<User> userList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = noteDataSnapshot.getValue(User.class);
                    System.out.println("userMail " + user.getUserEmail());
                    userList.add(user);
                    System.out.println("1 returnList().size() =" + userList.size());
                }
                DataHolder.getInstance().setUserListe(userList);
                System.out.println("Dataholder");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Database error--------------------------------------------------------------");
                System.out.println(databaseError);
            }

        });
    }

    public void addUserToFirebase(User user, String userId) {
        mDatabase.child(MainActivity.context.getResources().getString(R.string.users)).child(userId).setValue(user);
        System.out.println("add user--------------------------------------------------");
    }

    public User getUserById(String userId) {
        final List<User> user = new ArrayList<>();
        mDatabase.child(MainActivity.context.getResources().getString(R.string.users)).child(userId).addValueEventListener(new ValueEventListener() {
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
