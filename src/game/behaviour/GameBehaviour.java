package game.behaviour;

import game.players.Player;
import game.ui.GameUI;

public class GameBehaviour {

    private Player player1;
    private Player player2;
    private int score1;
    private int score2;
    private boolean isPlayer1;
    private int streakTurnCount;

    public GameBehaviour(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        score1 = score2 = 0;
        isPlayer1 = true;
        streakTurnCount = 1;
    }

    public GameState getInitialState() {
        return new GameState(player1, player2, score1, score2, false,
                isPlayer1 ? player1 : player2);
    }

    public GameState doTurn(GameUI ui) throws InterruptedException {
        if (!ui.hasCardPairsLeft()) {
            return new GameState(player1, player2, score1, score2, true, null);
        }

        if (isPlayer1) {
            boolean didScore = player1.doTurn(ui);
            if (didScore) {
                score1 += player1.getPairScoreModifier() * streakTurnCount;
                streakTurnCount++;
            } else {
                score1 += player1.getMismatchScoreModifier();
                isPlayer1 = false;
                streakTurnCount = 1;
            }

        }else{
            boolean didScore = player2.doTurn(ui);
            if (didScore) {
                score2 += (player2.getPairScoreModifier() * streakTurnCount) * 0.9;
                streakTurnCount++;
            } else {
                score2 += player2.getMismatchScoreModifier() * 1.1;
                isPlayer1 = true;
                streakTurnCount = 1;
            }
        }

        return new GameState(player1, player2, score1, score2, false,
                isPlayer1 ? player1 : player2);

    }


}
