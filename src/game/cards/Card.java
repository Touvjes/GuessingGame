package game.cards;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import java.awt.Color;

public class Card extends JButton {
    private ImageIcon backSide;
    private ImageIcon frontSide;
    private SpecialCardBehaviour specialCardBehaviour;
    private boolean isFlipped;
    private Border backBorder;

    public Card(ImageIcon backSide, ImageIcon frontSide, SpecialCardBehaviour specialCardBehaviour) {
        this.backSide = backSide;
        this.frontSide = frontSide;
        this.specialCardBehaviour = specialCardBehaviour;
        isFlipped = false;
        backBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    }

    public void showFront() {
        setIcon(frontSide);
        setBorder(backBorder);
    }

    public void showBack() {
        setIcon(backSide);
        setBorder(backBorder);
    }

    public void flipFront() {
        showFront();
        setBorder(specialCardBehaviour.getBorder());
        isFlipped = true;
    }

    public void flipBack() {
        showBack();
        isFlipped = false;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public boolean doesMatch(Card secondCard){
        return frontSide.equals(secondCard.frontSide);
    }


    public SpecialCardBehaviour getSpecialCardBehaviour() {
        return specialCardBehaviour;
    }
}
