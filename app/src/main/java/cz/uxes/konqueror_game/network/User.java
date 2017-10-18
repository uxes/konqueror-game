package cz.uxes.konqueror_game.network;

/**
 * Created by uxes on 18.10.17.
 */

class User {
    private String nick;
    private String score;
    private Integer level;

    public User(String nick, String score, Integer level) {
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

    public void setScore(String score) {
        this.score = score;
    }

    public void levelUp() {
        this.level += 1;
    }
}
