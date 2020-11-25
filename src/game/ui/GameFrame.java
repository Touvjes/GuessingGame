package game.ui;

import game.behaviour.GameBehaviour;
import game.behaviour.GameSettings;
import game.behaviour.GameState;
import game.behaviour.GameTask;
import game.cards.Card;
import game.cards.SpecialCardBehaviour;
import game.loaders.ImageLoader;
import game.scoring.ScoreKeeper;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class GameFrame extends JFrame {

    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 100;
    private static final int SCORE_BOARD_HEIGHT = 50;


    private JPanel gameArea;
    private JPanel scoreBoard;
    private JLabel score;

    private JPanel turnViewer;
    private JLabel turnViewerLabel;

    private List<Card> cards;

    private GameBehaviour gameBehaviour;
    private Thread gameThread;

    private ScoreKeeper scoreKeeper;

    private final Lock cardListenLock;
    private final Condition cardListenCondition;
    private final CardClickListener cardClickListener;

    public GameFrame(GameSettings settings, ImageLoader imageLoader, GameBehaviour gameBehaviour, ScoreKeeper scoreKeeper) {
        this.scoreKeeper = scoreKeeper;
        cardListenLock = new ReentrantLock();
        cardListenCondition = cardListenLock.newCondition();
        cardClickListener = new CardClickListener(cardListenLock, cardListenCondition);
        this.gameBehaviour = gameBehaviour;

        int width = settings.getBoardWidth() * CARD_WIDTH;
        int height = settings.getBoardHeight() * CARD_WIDTH + SCORE_BOARD_HEIGHT;

        setSize(width, height);
        setResizable(false);
        makeScoreboard(gameBehaviour.getInitialState());
        makeCards(settings, imageLoader);
        makeTurnView();
    }

    public void start() {
        GameUI gameUI = new GameUIImpl(this);
        gameThread = new Thread(new GameTask(gameBehaviour, this, gameUI));
        gameThread.start();
    }

    public void stop() {
        gameThread.interrupt();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    private void makeTurnView() {
        turnViewer = new JPanel();
        turnViewerLabel = new JLabel("");
        turnViewer.add(turnViewerLabel);

        add(turnViewer, BorderLayout.NORTH);
        turnViewer.setVisible(false);
    }

    private void showTurnView(String text, CountDownLatch waitLatch) {
        turnViewerLabel.setText(text);
        turnViewer.setVisible(true);

        Timer timer = new Timer(2000, (e)-> {
            for (Card card : getUnflippedCards()) {
                card.showBack();
            }

            turnViewer.setVisible(false);
            waitLatch.countDown();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void makeCards(GameSettings gameSettings, ImageLoader imageLoader) {
        int cardNumber = gameSettings.getBoardWidth() * gameSettings.getBoardHeight();
        List<ImageIcon> imageList = imageLoader.loadImages(cardNumber / 2);
        ImageIcon backside = imageLoader.loadBackside();
        gameArea = new JPanel();
        gameArea.setLayout(new GridLayout(gameSettings.getBoardHeight(), gameSettings.getBoardWidth()));

        cards = new ArrayList<>(cardNumber);

        //add buttons
        for (int i = 0; i < (cardNumber / 2); i++){
            ImageIcon frontside = imageList.remove(0);
            //create card
            Card card = new Card(backside, frontside, SpecialCardBehaviour.rollBehaviour());
            card.flipBack();
            card.addActionListener(cardClickListener);
            card.setSize(CARD_WIDTH, CARD_HEIGHT);
            cards.add(card);

            card = new Card(backside, frontside, SpecialCardBehaviour.rollBehaviour());
            card.flipBack();
            card.addActionListener(cardClickListener);
            card.setSize(CARD_WIDTH, CARD_HEIGHT);
            cards.add(card);
        }

        Collections.shuffle(cards);
        for (Card card : cards) {
            gameArea.add(card);
        }

        add(gameArea, BorderLayout.CENTER);
    }

    public void makeScoreboard(GameState state) {
        /*
        [game area]
        [SCORE BOARD]
         */

        scoreBoard = new JPanel();
        score = new JLabel("ScoreBoard");
        JButton highscore = new JButton("Highscores");
        setScore(state.getPlayer1().getName(), state.getScore1(),
                state.getPlayer2().getName(), state.getScore2());
        scoreBoard.add(score);
        scoreBoard.add(highscore);
        highscore.addActionListener(new HighscoreActionListener(scoreKeeper, this));
        add(scoreBoard, BorderLayout.SOUTH);


    }

    private void setScore(String name1, int score1, String name2, int score2) {
        score.setText(String.format("%s: %d\t%s: %d", name1, score1, name2, score2));
    }

    public boolean updateState(GameState state) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        SwingUtilities.invokeLater(()-> {
            setScore(state.getPlayer1().getName(), state.getScore1(),
                    state.getPlayer2().getName(), state.getScore2());

            if (state.isFinished()) {
                String winnerName = state.getScore1() > state.getScore2() ?
                        state.getPlayer1().getName() :
                        state.getPlayer2().getName();

                scoreKeeper.addScore(state.getPlayer1(), state.getScore1());
                scoreKeeper.addScore(state.getPlayer2(), state.getScore2());

                try {
                    scoreKeeper.saveScores();
                } catch (IOException e) {
                    System.err.println("Error saving scores");
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(this,
                        "Game finished. Winner: " + winnerName);
                latch.countDown();
            } else {
                /*JOptionPane.showMessageDialog(this,
                        "Turn: " + state.getNextPLayer().getName());*/

                showTurnView(String.format("Turn: %s", state.getNextPLayer().getName()),
                        latch);
            }
        });

        latch.await();
        //puts thread to sleep until counter is 0
        return !state.isFinished();
    }

    public List<Card> getUnflippedCards() {
        List<Card> notFlipped = new ArrayList<>();
        for (Card card : cards) {
            if (!card.isFlipped()) {
                notFlipped.add(card);
            }
        }

        return notFlipped;
    }

    //checks if pairs remain on board
    public boolean hasCardPairsLeft() {
        for (Card card : cards) {
            if (!card.isFlipped()) {
                return true;
            }
        }

        return false;
    }


    public Card waitForCardClick() throws InterruptedException {
        // bouncer
        cardListenLock.lock();
        //acquires lock
        try {
            // pipe
            cardListenCondition.await();
            // memory
            return cardClickListener.getLastClicked();
        } finally {
            cardListenLock.unlock();
            //releases lock
        }
    }

}
