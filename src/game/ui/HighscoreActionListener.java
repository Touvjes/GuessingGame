package game.ui;

import game.scoring.Score;
import game.scoring.ScoreKeeper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class HighscoreActionListener implements ActionListener {
    private ScoreKeeper scoreKeeper;
    private JFrame frame;

    public HighscoreActionListener(ScoreKeeper scoreKeeper, JFrame frame) {
        this.scoreKeeper = scoreKeeper;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Score> scores = scoreKeeper.getScores();
        scores.sort(Comparator.comparingInt(Score::getScore).reversed());

        //define table rows as 2d array
        Object[][] rows = new Object[scores.size()][2];
        for (int i = 0; i < rows.length; i++) {
            Score score = scores.get(i);
            rows[i][0] = score.getPlayerName();
            rows[i][1] = score.getScore();
        }

        //make table uneditable
        Object[] cols = {"Name", "Score"};
        TableModel model = new DefaultTableModel(rows, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JOptionPane.showMessageDialog(frame, new JScrollPane(table));
    }
}
