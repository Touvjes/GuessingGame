package game.ui;

import game.cards.Card;

import java.util.List;

public interface GameUI {
    Card waitForCardClick() throws InterruptedException;
    void show(Card card);
    void hide(Card... card);
    void showAndWait(Card card) throws InterruptedException;

    List<Card> getUnflippedCards();
    boolean hasCardPairsLeft();


}
