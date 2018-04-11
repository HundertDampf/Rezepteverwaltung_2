package ch.bbbaden.m335.rezepteverwaltung.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 02.03.2018.
 */
@Entity(tableName = "rezepte")
public class Rezept {

    @PrimaryKey
    @NonNull
    @Getter
    @Setter
    private String rezeptId;

    @Getter
    @Setter
    private String rezeptName;

    @Getter
    @Setter
    private String rezeptAuthor;

    @Getter
    @Setter
    private String rezeptZutatenString;

    @Exclude
    @Ignore
    private List<String> rezeptZutaten;

    @Getter
    @Setter
    private String rezeptZubereitung;

    @Getter
    @Setter
    private String rezeptDauer;

    @Getter
    @Setter
    private boolean rezeptOnline;

    @Getter
    @Setter
    private boolean rezeptPublic;

    public Rezept() {
    }

    public Rezept(String rezeptId, String rezeptName, String rezeptZubereitung, String rezeptDauer, boolean rezeptStatus, boolean rezeptDbMethod) {
        this.rezeptId = rezeptId;
        this.rezeptName = rezeptName;
        this.rezeptZubereitung = rezeptZubereitung;
        //TODO Zutaten
        this.rezeptDauer = rezeptDauer;
        this.rezeptOnline = rezeptStatus;
        this.rezeptPublic = rezeptDbMethod;
    }

    public void setRezeptZutaten(List<String> zutaten) {
        System.out.println("Setter Zutaten List Grösse: " + zutaten.size());
        rezeptZutaten = zutaten;
        String serializedZutaten = "";
        for (int i = 0; i < zutaten.size(); i++) {
            System.out.println("serializer loop EDFAGRREAAREDYGRAGZHXSDHT     " + i);
            serializedZutaten += zutaten.get(i) + ",";
        }
        setRezeptZutatenString(serializedZutaten);
    }

    public List<String> getRezeptZutaten() {
        if (rezeptZutaten == null) {
            if (getRezeptZutatenString() != null || getRezeptZutatenString() == "") {
                rezeptZutaten = new ArrayList<>();
                String[] zutatenArray = getRezeptZutatenString().split(",");
                for (int i = 0; i < zutatenArray.length; i++) {
                    rezeptZutaten.add(zutatenArray[i]);
                }
            }
        }
        return rezeptZutaten;
    }
}

