package game.behaviour;

import game.players.CheatingComputerPlayer;
import game.players.DumbComputerPlayer;
import game.players.HumanPlayer;
import game.players.Player;

public enum GameMode {
    PLAYER_VS_PLAYER {
        @Override
        public GameBehaviour createBehavior(GameSettings settings) {
            return new GameBehaviour(
                    new HumanPlayer(settings.getName1()),
                    new HumanPlayer(settings.getName2())
            );
        }
    },
    PLAYER_VS_COMPUTER {
        @Override
        public GameBehaviour createBehavior(GameSettings settings) {
            Player computer;
            switch (settings.getDifficulty()) {
                case 0:
                    computer = new DumbComputerPlayer();
                    break;
                case 1:
                    computer = new CheatingComputerPlayer(20);
                    break;
                case 2:
                    computer = new CheatingComputerPlayer(50);
                    break;
                case 3:
                    computer = new CheatingComputerPlayer(100);
                    break;
                default:
                    throw new IllegalArgumentException("unknown difficulty");
            }
            return new GameBehaviour(
                    new HumanPlayer(settings.getName1()),
                    computer
            );
        }
    },
    PLAYER_VS_SELF {
        @Override
        public GameBehaviour createBehavior(GameSettings settings) {
            return new GameBehaviour(
                    new HumanPlayer(settings.getName1()),
                    new HumanPlayer(settings.getName1())
            );
        }
    }
    ;


    public abstract GameBehaviour createBehavior(GameSettings settings);
}
