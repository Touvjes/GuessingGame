package game.behaviour;

public class GameSettings {
    private int boardHeight;
    private int boardWidth;
    private String name1;
    private String name2;
    private GameMode mode;
    private int difficulty;

    public GameSettings(int boardHeight, int boardWidth, String name1, String name2, GameMode mode, int difficulty) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.name1 = name1;
        this.name2 = name2;
        this.mode = mode;
        this.difficulty = difficulty;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public GameMode getMode() {
        return mode;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
