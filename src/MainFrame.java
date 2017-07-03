import javax.swing.*;
import java.awt.*;


class MainFrame extends JFrame {
    static Runnable runnable = new MainRunnable();
    public static Thread t;
    static GameOverPanel panel;


    MainFrame() throws HeadlessException {
        t = new Thread(runnable);
        t.start();
        setAlwaysOnTop(true);
        setFocusable(true);
        setTitle("Minesweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        panel = new GameOverPanel();
        add(panel, BorderLayout.NORTH);
    }

    public static GameOverPanel getPanel() {
        return panel;
    }

    void mineSweeper() {
        add(new Canvas());
        pack();
        setLocationRelativeTo(null);
    }

    void winTheGame() {
        add(new GameWonPanel());
        pack();
        setLocationRelativeTo(null);
    }


    public class GameWonPanel extends JPanel {
        Image image = new ImageIcon("res/fireworks.jpg").getImage();

        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, Canvas.WIDTH, Canvas.HEIGHT, null);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(Canvas.WIDTH, Canvas.HEIGHT);
        }
    }
}
