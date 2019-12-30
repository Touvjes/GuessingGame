import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GameFrame extends JFrame {
    //images
    ImageIcon cross = new ImageIcon(this.getClass().getResource("Icons/cross.png"));
    ImageIcon redCross = new ImageIcon(this.getClass().getResource("Icons/redCross.png"));
    ImageIcon logo = new ImageIcon(this.getClass().getResource("Icons/logo.png"));
    ImageIcon gem = new ImageIcon(this.getClass().getResource("Icons/gem.png"));
    ImageIcon emerald = new ImageIcon(this.getClass().getResource("Icons/emerald.png"));
    ImageIcon goblet = new ImageIcon(this.getClass().getResource("Icons/goblet.png"));
    ImageIcon amethyst = new ImageIcon(this.getClass().getResource("Icons/amethyst.png"));

    //properties
    // its necessary for either grid_height or grid_width to be even to ensure even number of squares
    private int grid_width = 5;
    private int grid_height = 4;
    private int frame_width = 500;
    private int frame_height = 500;
    private int num_buttons = grid_width * grid_height;

    private JPanel gameArea;
    private JPanel scoreBoard;
    private JLabel score;

    private String player;

    private JButton[] buttons;
    private boolean[] buttonsPress;
    private ImageIcon[] imgArray;
    GameState gameState;


    public GameFrame() {
        gameState = new GameState();
        setSize(frame_width, frame_height);
        setResizable(false);
        makeImgArray();
        makeButtons();
        makeScoreboard();
    }

    public void makeButtons() {
        gameArea = new JPanel();
        gameArea.setLayout(new GridLayout(grid_width, grid_height));
        buttons = new JButton[(num_buttons)];
        buttonsPress = new boolean[(num_buttons)];

        //add buttons
        for (int i = 0; i < (num_buttons); i++) {
            buttons[i] = new JButton();
            gameArea.add(buttons[i]);
            buttons[i].setIcon(cross);
            buttonsPress[i] = false;
            buttons[i].addActionListener(new ButtonListener());
        }
        add(gameArea);
    }

    public void makeScoreboard(){
        scoreBoard = new JPanel();
        score = new JLabel("ScoreBoard");
        score.add (scoreBoard);
        add(score, BorderLayout.SOUTH);
    }

    public ImageIcon[] makeImgArray() {
        ImageIcon[] imgs = new ImageIcon[4];
        imgs[0] = gem;
        imgs[1] = emerald;
        imgs[2] = goblet;
        imgs[3] = amethyst;

        imgArray = new ImageIcon[num_buttons];

        int i = 0;
        while (Arrays.asList(imgArray).subList(0, num_buttons).contains(null)){
            int rand1 = (int) (Math.random() * num_buttons);
            int rand2 = (int) (Math.random() * num_buttons);
            while (rand1 == rand2){
                rand2 = (int) (Math.random() * num_buttons);
            }
            if (imgArray[rand1] == null && imgArray[rand2] == null){
                imgArray[rand1] = imgs[i];
                imgArray[rand2] = imgs[i];
                System.out.println("" + rand1 + imgArray[rand1]+ "\n" + rand2 + imgArray[rand2]);
                i++;
                if (i == imgs.length){
                    i = 0;
                }

            }
        }
        return imgArray;
    }

    public ImageIcon[] getImgArray() {
        return imgArray;
    }

    public void unflip() {
        int card1 = gameState.cardsFlipped.get(0);
        int card2 = gameState.cardsFlipped.get(1);
        buttons[card1].setIcon(cross);
        buttons[card2].setIcon(cross);
        buttonsPress[card1] = false;
        buttonsPress[card2] = false;
    }

    public void scorePoint() {
        if (imgArray[gameState.cardsFlipped.get(0)] != imgArray[gameState.cardsFlipped.get(1)]) {
            JOptionPane.showMessageDialog(score, "Oops, Try Again!");
            unflip();
        } else {
            gameState.incScore();
            score.setText(gameState.getCurrentPlayer() + "'s Score: " + (gameState.getScore()));
            JOptionPane.showMessageDialog(score, "Nice, you got a point!");
        }
        gameState.resetFlip();
    }

    public void resetBoard(){
        //TODO
    }

    //inner class
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < (num_buttons); i++) {
                if (event.getSource() == buttons[i] && !buttonsPress[i]) {
                    gameState.flipCard(i);
                    buttonsPress[i] = true;
                    ((JButton) (event.getSource())).setIcon(imgArray[i]);
                    if (gameState.cardsFlipped.size() == 2) {
                        scorePoint();
                    }
                }
            }
        }
    }
}




