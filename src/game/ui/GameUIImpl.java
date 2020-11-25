package game.ui;

import game.cards.Card;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.List;

public class GameUIImpl implements GameUI {

    private GameFrame gameFrame;

    public GameUIImpl(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public Card waitForCardClick() throws InterruptedException {
        return gameFrame.waitForCardClick();
    }

    @Override
    public void show(Card card) {
        SwingUtilities.invokeLater(()-> {
            card.flipFront();

            card.getSpecialCardBehaviour().run(this, card);
        });
    }

    @Override
    public void hide(Card... cards) {
        SwingUtilities.invokeLater(()-> {
            Timer timer = new Timer(2000, (e)-> {
                for (Card card : cards) {
                    card.flipBack();
                }
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    @Override
    public void showAndWait(Card card) throws InterruptedException {
        show(card);
        Thread.sleep(1000);
    }

    @Override
    public List<Card> getUnflippedCards() {
        return gameFrame.getUnflippedCards();
    }

    @Override
    public boolean hasCardPairsLeft() {
        return gameFrame.hasCardPairsLeft();
    }
}
