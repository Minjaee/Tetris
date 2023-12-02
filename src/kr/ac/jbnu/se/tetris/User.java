package src.kr.ac.jbnu.se.tetris;

public class User {
    private final String id;
    private final String password;
    private final int score;

    public User(String id, String password, int score) {
        this.id = id;
        this.password = password;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    public int getScore() {
        return score;
    }
}
