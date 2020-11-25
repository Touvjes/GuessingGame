package game.ui;

import game.behaviour.GameBehaviour;
import game.behaviour.GameMode;
import game.behaviour.GameSettings;
import game.loaders.ImageLoader;
import game.loaders.RuleLoader;
import game.scoring.ScoreKeeper;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

public class MainFrame extends JFrame {

    private File selectedThemeFolder = null;

    public MainFrame(ScoreKeeper scoreKeeper, RuleLoader ruleLoader) {
        super("Memory Game");

        //Window
        JFrame FRAME = this;
        this.setSize(500, 350);
        setResizable(false);
        this.setLayout(new FlowLayout());

        //elements
        JButton startButton = new JButton("Start");
        JButton highscore = new JButton("High Score List");
        JButton rules = new JButton("Rules");
        JButton chooseTheme = new JButton("Choose Theme");
        JButton resetTheme = new JButton("Reset Theme");
        JLabel themeName = new JLabel("Default Theme");
        JComboBox<GameMode> mode = new JComboBox<>(GameMode.values());
        JTextField name1 = new JTextField("Player 1");
        name1.setPreferredSize(new Dimension(100, 25));
        JTextField name2 = new JTextField("Player 2");
        name2.setPreferredSize(new Dimension(100, 25));
        JSlider difficulty = new JSlider(JSlider.HORIZONTAL, 0, 3, 0);
        difficulty.setEnabled(false);
        Hashtable<Integer, JLabel> difficultyLabels = new Hashtable<>();
        difficultyLabels.put(0, new JLabel("Easy"));
        difficultyLabels.put(1, new JLabel("Medium"));
        difficultyLabels.put(2, new JLabel("Hard"));
        difficultyLabels.put(3, new JLabel("Impossible"));
        difficulty.setLabelTable(difficultyLabels);
        difficulty.setPaintLabels(true);
        difficulty.setPaintTicks(true);
        difficulty.setMajorTickSpacing(1);
        difficulty.setSnapToTicks(true);
        JSpinner boardHeight = new JSpinner();
        JSpinner boardWidth = new JSpinner();
        boardHeight.setValue(6);
        boardWidth.setValue(6);

        //element containers
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel(new BorderLayout());
        jp4.setPreferredSize(new Dimension(300, 50));
        JPanel jp5 = new JPanel();
        jp5.setPreferredSize(new Dimension(500, 100));
        JPanel jp6 = new JPanel();

        //adding elements to containers
        jp1.add(highscore);
        jp1.add(rules);
        add(jp1);

        jp2.add(boardHeight);
        jp2.add(boardWidth);
        add(jp2);

        jp3.add(mode);
        jp3.add(startButton);
        add(jp3);

        jp4.add(difficulty);
        add(jp4);

        jp5.add(name1);
        jp5.add(name2);
        add(jp5);

        jp6.add(themeName);
        jp6.add(chooseTheme);
        jp6.add(resetTheme);
        add(jp6);

        //Single-use listeners

        mode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch ((GameMode) e.getItem()) {
                    case PLAYER_VS_COMPUTER:
                        difficulty.setEnabled(true);
                        name2.setEnabled(false);
                        break;
                    case PLAYER_VS_SELF:
                        difficulty.setEnabled(false);
                        name2.setEnabled(false);
                        break;
                    case PLAYER_VS_PLAYER:
                        difficulty.setEnabled(false);
                        name2.setEnabled(true);
                        break;
                }
            }
        });


        highscore.addActionListener(new HighscoreActionListener(scoreKeeper, this));

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display a rule dialog
                try {
                    String rules = ruleLoader.loadRules();
                    JTextArea rulesTextArea = new JTextArea(rules);
                    rulesTextArea.setEditable(false);
                    JOptionPane.showMessageDialog(FRAME, rulesTextArea);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(FRAME, ex.getMessage(), "Error loading rules",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        chooseTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setDialogTitle("Choose Theme Folder");

                if (fileChooser.showOpenDialog(FRAME) == JFileChooser.APPROVE_OPTION) {
                    selectedThemeFolder = fileChooser.getSelectedFile();
                    themeName.setText(selectedThemeFolder.getName());
                }
            }
        });

        resetTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedThemeFolder = null;
                themeName.setText("Default Theme");


            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((int) boardHeight.getValue() * (int) boardWidth.getValue() % 2 != 0){
                    JOptionPane.showMessageDialog(FRAME, "Board must result in even number of squares. " +
                                    "Make sure either row or column is even.", "Failed to load Board",
                            JOptionPane.ERROR_MESSAGE);
                return;}
                try {
                    GameSettings settings = new GameSettings(
                            (int) boardHeight.getValue(),
                            (int) boardWidth.getValue(),
                            name1.getText(),
                            name2.getText(),
                            (GameMode) mode.getSelectedItem(),
                            difficulty.getValue()
                    );
                    GameBehaviour behaviour = settings.getMode().createBehavior(settings);

                    ImageLoader imageLoader;

                    if (selectedThemeFolder != null) {
                        imageLoader = new ImageLoader(selectedThemeFolder);
                    } else {
                        imageLoader = new ImageLoader(new File(getClass().getResource("/Icons").toURI()));
                    }

                    GameFrame gameFrame = new GameFrame(settings, imageLoader, behaviour, scoreKeeper);
                    gameFrame.setTitle("Game");
                    gameFrame.setLocationRelativeTo(null);
                    gameFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            gameFrame.stop();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            startScreen(scoreKeeper, ruleLoader);
                        }
                    });
                    gameFrame.setVisible(true);
                    gameFrame.start();
                } catch (URISyntaxException ex) {
                    JOptionPane.showMessageDialog(FRAME, ex.getMessage(), "Failed to load theme",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    //methods

    public static void startScreen(ScoreKeeper scoreKeeper, RuleLoader ruleLoader) {
        //program
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new MainFrame(scoreKeeper, ruleLoader);
                frame.setTitle("Start Screen");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
