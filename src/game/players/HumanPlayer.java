package game.players;

import game.cards.Card;
import game.ui.GameUI;

public class HumanPlayer implements Player {
    private String name;

    public HumanPlayer(String name) {
        this.name = name;
    }

    @Override
    public boolean doTurn(GameUI ui) throws InterruptedException {
        //select first card
        Card card1 = ui.waitForCardClick();
        ui.show(card1);

        //select second card
        Card card2 = ui.waitForCardClick();
        ui.show(card2);

        if (card1.doesMatch(card2)){
            return true;
        }

        ui.hide(card1, card2);

        return false;
    }

    @Override
    public String getName() {
        return name;
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
