public class Game {

    static MainFrame frame;
@SuppressWarnings("Duplicates")
    public static void main(String[] args) {

        frame = new MainFrame();
        frame.mineSweeper();
    }

    static void gameWin(){
        frame.dispose();
        frame = new MainFrame();
        frame.winTheGame();
    }
}
