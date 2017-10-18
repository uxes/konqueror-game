package cz.uxes.konqueror_game.network;

/**
 * Created by uxes on 18.10.17.
 */

public enum WsConnectionSingleton {
    INSTANCE;

    public WsConnection connection = new WsConnection();
}
