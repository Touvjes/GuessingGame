import java.util.ArrayList;

public class GameState {

    private String gameMode;
    private String[] players;
    private String currentPlayer;
    private int score;

    //an arraylist which holds the indexes of the currently flipped cards
    public ArrayList<Integer> cardsFlipped = new ArrayList<>(2);

    public GameState(){
        //currentPlayer = players[0];
    }


    //1 = human vs human
    //2 = human vs comp
    //else = single
    //could have been a case switch
    public void setGameMode(int mode){
        if (mode == 1){
            players[0] = "Player 1 ";
            players[1] = "Player2";
        } else if (mode == 2){
            players[0] = "Player 1";
            players[1] = "Computer";
        }else{
            players[0] = "Player1";
        }

    }
    public String getGameMode() {
        return gameMode;
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

    public void incScore() {
        this.score++;
        System.out.println("score: " + score);
    }

    public void wait(int mili){
        try
        {
            Thread.sleep(mili);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }

    //array lists size starts at 1?
    public void flipCard(int cardIndex) {
        cardsFlipped.add(cardIndex);
        System.out.println("New flipped Cards: " + cardsFlipped);
        if (cardsFlipped.size() == 2){
            scorePoint();
        }
    }

    public void resetFlip(){
        cardsFlipped = new ArrayList<>(2);
    }

    public void scorePoint(){
        //TODO
        //make buttons not clickable
        {
            //endTurn()
            //unflip();
        }
    }


}



