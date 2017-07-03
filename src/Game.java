public class Game {

    static MainFrame frame;

    public static void main(String[] args) {
        frame = new MainFrame();
        frame.mineSweeper();
    }

    static void gameWin(){
        MainPanel.getT().cancel();
        MainPanel.setCounter(0);
        frame.dispose();
        frame = new MainFrame();
        frame.winTheGame();
    }

    static void newGame(){
        MainPanel.getT().cancel();
        MainPanel.setCounter(0);

        frame.dispose();
        frame = new MainFrame();
        try{
            Thread.sleep(50);
        }catch (InterruptedException e){}
        frame.mineSweeper();
    }
}
