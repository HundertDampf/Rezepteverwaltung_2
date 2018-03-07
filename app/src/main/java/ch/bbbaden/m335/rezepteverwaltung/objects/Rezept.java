package ch.bbbaden.m335.rezepteverwaltung.objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 02.03.2018.
 */

public class Rezept {
    @Getter
    @Setter
//    @PrimaryKey
    private String rezeptId;

    @Getter
    @Setter
    private String rezeptName;

    @Getter
    @Setter
    private String rezeptAuthor;

//    @Getter
//    @Setter
//    private List<String>[] rezeptZutaten;

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
}

