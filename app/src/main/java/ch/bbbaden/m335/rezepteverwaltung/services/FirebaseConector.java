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

        if (DataHolder.getInstance().getUserListe() != null) {
            for (int i = 0; i < DataHolder.getInstance().getUserListe().size(); i++) {
                if (DataHolder.getInstance().getUserListe().get(i).getUserLongId() != null) {
                    mDatabase.child(MainActivity.context.getResources().getString(R.string.dbpublic)).child(DataHolder.getInstance().getUserListe().get(i).getUserLongId()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        } else {
            System.out.println("Döödö");
        }
//     mDatabase.child("publicRezepte").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
//                System.out.println("From Firebase rezepte public");
//                int i = 1;
//                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
//                    returnList.add(noteDataSnapshot.getValue(Rezept.class));
//                    System.out.println("Loopdeliloop #" + i);
//                    i += 1;
//                }
//
//                if (returnList.size() == 0) {
//                    System.out.println("Alarm! Liste ist leer");
//                }
//                DatabaseConector.addRezepteFromFirebase(returnList);
//            }
//
//
//            @Override
//            public void onChildRemoved(DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//            @Override
//            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
//
//            }
//
//
//        });
        mDatabase.child("privateRezepte").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("From Firebase rezepte private");
                int i = 1;
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    returnPrivateList.add(noteDataSnapshot.getValue(Rezept.class));
                    System.out.println("Loopdelilooplooop private #" + i);
                    i += 1;
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
        mDatabase.child("publicRezepte").child("" + userId).addListenerForSingleValueEvent(new ValueEventListener() {

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
        System.out.println(MainActivity.context.getResources().getString(R.string.users));
        mDatabase.child(MainActivity.context.getResources().getString(R.string.users)).addValueEventListener(new ValueEventListener() {
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

    public void getUserById(String userId) {
        System.out.println("getUserbyID *****************************************************+***********************-+*");
        mDatabase.child(MainActivity.context.getResources().getString(R.string.users)).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("UPDATE USER &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                User user = dataSnapshot.getValue(User.class);
                User localUser = DatabaseConector.getUserByMail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                if (localUser == null) {
                    DatabaseConector.addUser(user);
                } else {
                    System.out.println("localMail " + localUser.getUserEmail());
                    System.out.println("fbmail " + user.getUserEmail());

                    if (localUser.getUserEmail().equals(user.getUserEmail())) {
                        System.out.println("delete and add called");
                        DatabaseConector.deleteUser(localUser);
                        DatabaseConector.addUser(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
