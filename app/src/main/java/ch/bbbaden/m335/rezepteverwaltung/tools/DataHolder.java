package ch.bbbaden.m335.rezepteverwaltung.tools;

import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.objects.Rezept;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 02.03.2018.
 */

public class DataHolder {
    @Setter
    @Getter
    private Rezept rezept;

    @Setter
    @Getter
    private List<Rezept> rezepteListe;


    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}

