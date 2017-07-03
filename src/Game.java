public class Game {

    static MainFrame frame;

    public static void main(String[] args) {
        frame = new MainFrame();
        frame.mineSweeper();
    }

    static void gameWin(){
        frame.dispose();
        frame = new MainFrame();
        frame.winTheGame();
    }

    static void newGame(){
        Game.frame.dispose();
        Game.frame = new MainFrame();
        Game.frame.mineSweeper();
    }
}
