package game.ui;

import game.cards.Card;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class CardClickListener implements ActionListener {

    private final Lock cardListenLock;
    private final Condition cardListenCondition;
    private Card lastClicked;

    CardClickListener(Lock cardListenLock, Condition cardListenCondition) {
        this.cardListenLock = cardListenLock;
        this.cardListenCondition = cardListenCondition;
    }

    public Card getLastClicked() {
        return lastClicked;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Card card = ((Card) e.getSource());
        if (card.isFlipped()) {
            return;
        }

        // bouncer
        cardListenLock.lock();
        // acquires lock
        try {
            // memory
            lastClicked = card;
            // pipe
            cardListenCondition.signal();
            //wakes up
        } finally {
            cardListenLock.unlock();
            //releases lock
        }
    }
}
