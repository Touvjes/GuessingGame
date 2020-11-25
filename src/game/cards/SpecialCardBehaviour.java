package game.cards;

import game.ui.GameUI;

import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum SpecialCardBehaviour {
    NONE(75, BorderFactory.createLineBorder(Color.BLACK, 1)) {
        @Override
        public void run(GameUI gameUI, Card selectedCard) {
        }
    },
    SHOW_BOARD(85, BorderFactory.createLineBorder(Color.BLUE, 5)) {
        @Override
        public void run(GameUI gameUI, Card selectedCard) {
            List<Card> cards = gameUI.getUnflippedCards();
            for (Card card : cards) {
                card.showFront();
            }

            Timer timer = new Timer(2000, (e)-> {
                for (Card card : cards) {
                    if (!card.isFlipped()) {
                        card.showBack();
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    },
    SHOW_MATCHES(100, BorderFactory.createLineBorder(Color.GREEN, 5)) {
        @Override
        public void run(GameUI gameUI, Card selectedCard) {
            List<Card> cards = gameUI.getUnflippedCards();
            List<Card> flippedCards = new ArrayList<>();
            for (Card card : cards) {
                if (card.doesMatch(selectedCard)) {
                    card.showFront();
                    flippedCards.add(card);
                }
            }

            Timer timer = new Timer(2000, (e)-> {
                for (Card card : flippedCards) {
                    if (!card.isFlipped()) {
                        card.showBack();
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    };

    private final int minRolled;
    private final Border border;

    SpecialCardBehaviour(int chance, Border border) {
        this.minRolled = chance;
        this.border = border;
    }

    public Border getBorder() {
        return border;
    }

    public static SpecialCardBehaviour rollBehaviour() {
        int rolled = (int) (Math.random() * 100);
        for (SpecialCardBehaviour behaviour : values()) {
            if (rolled < behaviour.minRolled) {
                return behaviour;
            }
        }

        throw new AssertionError();
    }

    public abstract void run(GameUI gameUI, Card card);
}
