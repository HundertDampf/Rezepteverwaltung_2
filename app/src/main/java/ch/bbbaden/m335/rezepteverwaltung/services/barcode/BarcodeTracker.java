package ch.bbbaden.m335.rezepteverwaltung.services.barcode;

import android.content.Context;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Noah on 02.03.2018.
 */

public class BarcodeTracker extends Tracker<Barcode> {
    private BarcodeGraphicTrackerCallback mListener;

    public interface BarcodeGraphicTrackerCallback {
        void onDetectedQrCode(Barcode barcode);
    }

    BarcodeTracker(Context listener) {
        mListener = (BarcodeGraphicTrackerCallback) listener;
    }

    @Override
    public void onNewItem(int id, Barcode item) {
        if (item.displayValue != null) {
            mListener.onDetectedQrCode(item);
        }
    }
}
