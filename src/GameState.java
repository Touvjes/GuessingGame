import java.util.ArrayList;

public class GameState {

    private String gameMode;
    private String currentPlayer;
    private int score;
    private int numFlipped;

    //an arraylist which holds the indexes of the currently flipped cards
    public ArrayList<Integer> cardsFlipped = new ArrayList<>(2);;

    public GameState(){
        numFlipped = 0;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCardsFlipped() {
        return numFlipped;
    }

    public void flipCard(int cardIndex) {
        cardsFlipped.add(cardIndex);
        numFlipped++;
    }

    public void resetCards(){

        cardsFlipped = new ArrayList<>(2);
    }



}



