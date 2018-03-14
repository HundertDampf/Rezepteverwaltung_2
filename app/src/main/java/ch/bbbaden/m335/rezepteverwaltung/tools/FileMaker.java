package ch.bbbaden.m335.rezepteverwaltung.tools;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ch.bbbaden.m335.rezepteverwaltung.activities.MainActivity;
import ch.bbbaden.m335.rezepteverwaltung.objects.User;

/**
 * Created by Noah on 05.02.2018.
 */

public class FileMaker {
    public void stringToDom(String toFile, String name, Context context) throws IOException {
        FileOutputStream outputStream;

        System.out.println(name + "    -------------  ------------------------------------------------" + toFile);
        if (context == null) {
            System.out.println("Context ist leer");
            context = MainActivity.context;
        }
        try {
            outputStream = context.openFileOutput(name.toLowerCase(), Context.MODE_PRIVATE);
            outputStream.write(toFile.getBytes("UTF-8"));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringFromFile(Context context, String file) {
        String returnString = "";
        String line;

        try {
            FileInputStream fin = context.openFileInput(file.toLowerCase());
            StringBuilder sb = new StringBuilder();
            InputStreamReader inputStream = new InputStreamReader(fin);
            BufferedReader bufferedReader = new BufferedReader(inputStream);

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            returnString = sb.toString();

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return returnString;
    }

    public String userToString(User user, String userId) {
        System.out.println("generiere File name: " + userId);
        try {
            FileOutputStream fos = MainActivity.context.openFileOutput(userId, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return "";
    }

    public User stringToUser(String userId) {
        System.out.println("getting file " + userId);
        User user;
        try {
            FileInputStream fileIn = new FileInputStream(userId);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            user = (User) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
        return user;
    }
}