import javax.swing.*;

public class BattleShip {
    public static void start_screen() {
        //program
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new StartScreen();
                frame.setTitle("Start Screen");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void start_game() {
        //program
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new GameFrame();
                frame.setTitle("Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main (String[]args)
    {
        start_game();
//        while (play_game == true){
//            start_game();
//        }
    }
}
