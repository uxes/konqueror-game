package cz.uxes.konqueror_game.network;

import android.util.Log;

/**
 * Created by uxes on 6.12.17.
 */

public class Realm {
    public Integer id;
    public String name;
    public double x;
    public double y;
    private boolean konquered;

    public Realm(Integer id, String name, double x, double y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.konquered = false;
    }

    public boolean isKonquered() {
        return konquered;
    }

    public void setKonquered() {
        Log.d("konquer", "nstavuj ho na obsahzeneho");
        this.konquered = true;
    }
}
