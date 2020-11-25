package game.players;


import game.ui.GameUI;

public interface Player {
    boolean doTurn(GameUI ui) throws InterruptedException;

    String getName();

    int getPairScoreModifier();
    int getMismatchScoreModifier();

}
