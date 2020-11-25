package game;

import game.loaders.RuleLoader;
import game.scoring.ScoreKeeper;
import game.ui.MainFrame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        ScoreKeeper scoreKeeper = new ScoreKeeper(Paths.get("scores.txt"));
        RuleLoader ruleLoader = new RuleLoader(Paths.get(ClassLoader.getSystemResource("Rules.txt").toURI()));
        try {
            scoreKeeper.loadScores();
        } catch (IOException e) {
            System.err.println("Failed to load scores");
            e.printStackTrace();
        }

        MainFrame.startScreen(scoreKeeper, ruleLoader);
    }

}
