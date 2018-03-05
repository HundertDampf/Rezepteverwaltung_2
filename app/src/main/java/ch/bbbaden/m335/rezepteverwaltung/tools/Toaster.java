package ch.bbbaden.m335.rezepteverwaltung.tools;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Noah on 02.03.2018.
 */

public class Toaster {
    public Toaster(Context context, String toToast, int duration) { //duration: 1=long, 0=short
        Toast toast = Toast.makeText(context, toToast, duration);
        toast.show();
    }

    public Toaster(View view, String toSnack, int duration) {
        Snackbar.make(view, toSnack, duration).setAction("Action", null).show(); //duration -0=Long, 1=Short, -2=unlimited
    }
}
