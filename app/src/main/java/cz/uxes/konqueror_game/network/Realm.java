package cz.uxes.konqueror_game.network;

/**
 * Created by uxes on 6.12.17.
 */

public class Realm {
    public Integer id;
    public String name;
    public double x;
    public double y;

    public Realm(Integer id, String name, double x, double y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
