package sample;

public class Player implements Comparable<Player>{
    private String nickname;
    private int score;
    private boolean isDrawing;

    public Player(String nickname, boolean isDrawing) {
        this.nickname = nickname;
        this.isDrawing = isDrawing;
        score = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public void addPoints(int points){
        score+=points;
    }

    @Override
    public int compareTo(Player o) {
        if(this.score < o.score)
            return -1;
        else if(this.score > o.score)
            return 1;
        else return this.nickname.compareToIgnoreCase(o.nickname);
    }
}
