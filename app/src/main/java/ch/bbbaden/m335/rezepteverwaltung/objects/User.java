package ch.bbbaden.m335.rezepteverwaltung.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import ch.bbbaden.m335.rezepteverwaltung.services.DatabaseConector;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 09.03.2018.
 */
@Entity(tableName = "user")
public class User implements Serializable {
    @Getter
    @Setter
    private long userShortId;

    @Getter
    @Setter
    private String userLongId;

    @Getter
    @Setter
    private String userName;

    @PrimaryKey
    @Getter
    @Setter
    @NonNull
    private String userEmail;

    public User getCurrentUser() {
        return DatabaseConector.getUserByMail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

}
