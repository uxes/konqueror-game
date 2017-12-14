package cz.uxes.konqueror_game.network;

/**
 * Created by uxes on 18.10.17.
 */

public class Player {
    private String nick;
    private String score;
    private Integer level;

    public Player() {}

    public Player(String nick, String score) {
        this.nick = nick;
        this.score = score;
    }
    public Player(String nick, String score, Integer level) {
        this.nick = nick;
        this.score = score;
        this.level = level;
    }

    public String getNick() {
        return nick;
    }

    public String getScore() {
        return score;
    }

    public Integer getLevel() {return level; }

    public void setScore(String score) {
        this.score = score;
    }

    public void levelUp() {
        this.level += 1;
    }
}
