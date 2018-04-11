package ch.bbbaden.m335.rezepteverwaltung.services;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.bbbaden.m335.rezepteverwaltung.objects.User;

/**
 * Created by Noah on 14.03.2018.
 */

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE userEmail IN (:userMail)")
    User loadUserByMail(String userMail);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

}
