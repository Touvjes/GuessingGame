package game.behaviour;

import game.players.Player;

public class GameState {
    private Player player1;
    private Player player2;
    private int score1;
    private int score2;
    private boolean isFinished;
    private Player nextPLayer;

    public GameState(Player player1, Player player2, int score1, int score2, boolean isFinished, Player nextPLayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = score1;
        this.score2 = score2;
        this.isFinished = isFinished;
        this.nextPLayer = nextPLayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public Player getNextPLayer() {
        return nextPLayer;
    }
}
