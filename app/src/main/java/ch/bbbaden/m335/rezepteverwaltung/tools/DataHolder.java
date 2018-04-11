package ch.bbbaden.m335.rezepteverwaltung.tools;

import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.objects.Recipe;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 02.03.2018.
 */

public class DataHolder {
    @Setter
    @Getter
    private Recipe recipe;

    @Setter
    @Getter
    private List<Recipe> rezepteListe;

    @Setter
    @Getter
    private List<User> userListe;

    @Getter
    @Setter
    private int saveId;

    @Setter
    @Getter
    User user;

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}

