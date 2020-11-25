package game.players;

import game.cards.Card;
import game.ui.GameUI;

import java.util.List;
import java.util.Random;

public class CheatingComputerPlayer implements Player {

    private int cheatChance;
    private Random random;

    public CheatingComputerPlayer(int cheatChance) {
        this.cheatChance = cheatChance;
        this.random = new Random();
    }

    @Override
    public boolean doTurn(GameUI ui) throws InterruptedException {
        List<Card> allCards = ui.getUnflippedCards();

        Card card1 = allCards.get(random.nextInt(allCards.size()));
        Card card2 = null;

        // random (0, x) == 0
        if (random.nextInt((100 - cheatChance) + 1) == 0) {
            for (Card card : allCards) {
                if (card.doesMatch(card1)) {
                    card2 = card;
                    break;
                }
            }
        } else {
            do {
                card2 = allCards.get(random.nextInt(allCards.size()));
            } while(card2.equals(card1));
        }

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
        return String.format("CHEAT CPU (%d %%)", cheatChance);
    }


    @Override
    public int getPairScoreModifier() {
        return 100;
    }

    @Override
    public int getMismatchScoreModifier() {
        return -50;
    }
}
