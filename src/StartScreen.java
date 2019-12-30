import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class StartScreen extends JFrame {
    private JSpinner h;
    private JSpinner w;
    public int gridHeight;
    public int gridWidth;
    private String[] modes = {"Player vs. Player", "Player vs. Computer", "Single Player"};

    public StartScreen() {
        super("Memory Game");
        this.setSize(500, 200);
        this.setLayout(new FlowLayout());
        JButton startbtn = new JButton("Start");
        JButton highscore = new JButton("High Score List");
        JButton rules = new JButton("Rules");
        JSlider diffslider = new JSlider(0,100,50);
        JComboBox mode = new JComboBox(modes);

        h = new JSpinner();
        w = new JSpinner();
        h.setValue(4);
        w.setValue(4);

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        jp1.add(highscore);
        jp1.add(rules);
        add(jp1);

        jp2.add(diffslider);
        jp2.add(h);
        jp2.add(w);
        add(jp2);

        jp3.add(mode);
        jp3.add(startbtn);
        add(jp3);

        //actionlisteners

        highscore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        startbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setHValue((Integer) h.getValue());
                setWValue((Integer) w.getValue());

                BattleShip.start_game();
            }
        });
    }

    private void setWValue(int val) {
        gridWidth = val;
    }

    private void setHValue(int val) {
        gridHeight = val;
    }

    public int getGridHeight() {
        System.out.println("height"+gridHeight);
        return gridHeight;
    }

    public int getGridWidth() {
        System.out.println("width"+gridWidth);
        return gridWidth;
    }
}
