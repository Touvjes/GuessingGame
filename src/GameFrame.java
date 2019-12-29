import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
    private int grid_width = 2;
    private int grid_height = 3;
    private int frame_width = 500;
    private int frame_height = 500;
    private int num_buttons =  grid_width*grid_height;


    private JPanel gameArea;
    private JPanel score;
    private JLabel scoreBoard;

    private JButton[] buttons;
    private boolean[] buttonsPress;
    private ImageIcon[] img;
    private ImageIcon[] imgArray;
    GameState gameState;


    public GameFrame() {
        this.gameState = new GameState();
        makeImgArray();
        makeGameGrid();
        setSize(frame_width, frame_height);
        setResizable(false);
    }

    public void makeGameGrid() {
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

    public ImageIcon[] makeImgArray(){
        img = new ImageIcon[4];
        img[0] = gem;
        img[1] = emerald;
        img[2] = goblet;
        img[3] = amethyst;

        imgArray = new ImageIcon[num_buttons];

        //limit is to make sure the amount of each image is proportionate.
        int limit = num_buttons/img.length;

        // populate array of images equal to number of buttons to serve as the underside of the cards
        // there is probably a more straightforward and robust way of doing this, but as long as the number of buttons
        // and the length of the array is equal, this approach will work.
        for(int i = 0; i < num_buttons; i++){
            int rand = (int) (Math.random() * img.length);
            imgArray[i] = img[rand];
        }
        return imgArray;
    }

    public ImageIcon[] getImgArray() {
        return imgArray;
    }



    //inner class
    class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            for (int i = 0; i < (num_buttons); i++)
            {
                if (event.getSource() == buttons[i] && buttonsPress[i] == false)
                {
                    gameState.flipCard(i);
                    buttonsPress[i] = true;
                    ((JButton) (event.getSource())).setIcon(imgArray[i]);

                    }
                }
            }
        }
    }



