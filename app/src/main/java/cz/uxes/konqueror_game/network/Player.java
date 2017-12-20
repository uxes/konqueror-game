package cz.uxes.konqueror_game.network;

/**
 * Created by uxes on 18.10.17.
 */

public class Player {
    private String id;
    private String nick;
    private Integer score;
    private Integer level;
    private String hostname;

    public Player() {
        this.id = null;
    }

    public Player(String nick, String hostname, String id) {
        this.id = id;
        this.nick = nick;
        this.hostname = hostname;
        this.score = 0;
    }
    public Player(String nick, Integer level, String hostname) {
        this.nick = nick;
        this.level = level;
        this.hostname = hostname;
        this.score = 0;
    }

    public String getNick() {
        return nick;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getLevel() {return level; }

    public void setScore(Integer score) {
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

    public void clearScore() {
        this.score = 0;
    }

    public void scoreUp() {
        this.score += 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
