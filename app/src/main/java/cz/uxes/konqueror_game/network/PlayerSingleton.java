package cz.uxes.konqueror_game.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uxes on 25.11.17.
 */

public enum PlayerSingleton {
    INSTANCE;

    private List<Player> playerList = new ArrayList<>();
    private List<Player> scoreList = new ArrayList<>();

    private PlayerSingleton() {}

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Player> scoreList) {
        this.scoreList = scoreList;
    }
}
