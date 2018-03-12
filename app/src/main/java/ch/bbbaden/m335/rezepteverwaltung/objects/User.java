package ch.bbbaden.m335.rezepteverwaltung.objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 09.03.2018.
 */

public class User {
    @Getter
    @Setter
    private long userShortId;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String userEmail;

}
