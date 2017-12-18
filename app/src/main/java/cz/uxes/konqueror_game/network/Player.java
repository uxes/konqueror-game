package cz.uxes.konqueror_game.network;

/**
 * Created by uxes on 18.10.17.
 */

public class Player {
    private String nick;
    private String score;
    private Integer level;
    private String hostname;

    public Player() {}

    public Player(String nick, String hostname) {
        this.nick = nick;
        this.hostname = hostname;
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

    public String getHostname() {
        return hostname;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
