package game.behaviour;


import game.ui.GameFrame;
import game.ui.GameUI;

public class GameTask implements Runnable {

    private GameBehaviour gameBehaviour;
    private GameFrame gameFrame;
    private GameUI gameUI;

    public GameTask(GameBehaviour gameBehaviour, GameFrame gameFrame, GameUI gameUI) {
        this.gameBehaviour = gameBehaviour;
        this.gameFrame = gameFrame;
        this.gameUI = gameUI;
    }

    @Override
    public void run() {
        //
        while (!Thread.interrupted()) {
            try {
                GameState state = gameBehaviour.doTurn(gameUI);
                if (!gameFrame.updateState(state)) {
                    break;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
