import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame {
    private JSpinner height;
    private JSpinner width;


    public StartScreen() {
        super("Memory Game");
        this.setSize(500, 300);
        this.setLayout(new FlowLayout());
        JButton startbtn = new JButton("Start");
        JButton highscore = new JButton("High Score List");
        JButton rules = new JButton("Rules");
        JSlider diffslider = new JSlider(0,100,50);
        JComboBox vs = new JComboBox();

        height = new JSpinner();
        width = new JSpinner();




        add(highscore);
        add(rules);
        add(diffslider);
        add(height);
        add(width);
        add(vs);
        add(startbtn);


        //actionlisteners
        startbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleShip.start_game();
            }
        });

        highscore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });




    }
    public int getGrid_height() {
        return (Integer) height.getValue();
    }

    public int getGrid_width() {
        return (Integer) width.getValue();
    }

    /*
    Options:
        see highscore
        select difficulty
        select grid size
        vs computer/human
        rules
        start game
    */

}
