package game.scoring;

import game.players.Player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ScoreKeeper {

    private final Path filePath;
    private final List<Score> scores;

    public ScoreKeeper(Path filePath) {
        this.filePath = filePath;
        scores = new ArrayList<>();
    }

    public List<Score> getScores() {
        return scores;
    }

    public void addScore(Player player, int score) {
        scores.add(new Score(player.getName(), score));
    }

    public void loadScores() throws IOException {
        /*
        playerName:score
         */
        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            int sepIdx = line.lastIndexOf(':');
            String playerName = line.substring(0, sepIdx);

            String scoreStr = line.substring(sepIdx + 1);
            int score = Integer.parseInt(scoreStr);

            scores.add(new Score(playerName, score));
        }
    }

    public void saveScores() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE)) {
            for (Score score : scores) {
                writer.write(String.format("%s:%d", score.getPlayerName(), score.getScore()));
                writer.newLine();
            }
        }
    }
}
