package game.players;

import game.cards.Card;
import game.ui.GameUI;

import java.util.List;
import java.util.Random;

public class DumbComputerPlayer implements Player {

    private Random random;

    public DumbComputerPlayer() {
        this.random = new Random();
    }

    @Override
    public boolean doTurn(GameUI ui) throws InterruptedException {
        List<Card> allCards = ui.getUnflippedCards();

        Card card1 = allCards.get(random.nextInt(allCards.size()));
        Card card2;

        //ensures card 2 is not the same as card 1
        do {
            card2 = allCards.get(random.nextInt(allCards.size()));
        } while(card2.equals(card1));

        ui.showAndWait(card1);
        ui.showAndWait(card2);

        if (card1.doesMatch(card2)) {
            return true;
        }

        ui.hide(card1, card2);
        return false;
    }

    @Override
    public String getName() {
        return "DUMB CPU";
    }

    @Override
    public int getPairScoreModifier() {
        return 50;
    }

    @Override
    public int getMismatchScoreModifier() {
        return -50;
    }
}
