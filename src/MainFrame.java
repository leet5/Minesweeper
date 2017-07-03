import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;


class MainFrame extends JFrame {
    private static MainPanel panel;
    private JPopupMenu popup;
    private static Canvas canvas;

    class TestAction extends AbstractAction
    {
        private String name;

        public TestAction(String name)
        {
            super(name);
            this.name = name;
        }

        public void actionPerformed(ActionEvent event)
        {
            if(name.equals("New")) {Game.newGame();}
            if(name.equals("Easy")){Canvas.setDifficulty(0);Game.newGame();}
            if(name.equals("Normal")){Canvas.setDifficulty(1);Game.newGame();}
            if(name.equals("Hard")){Canvas.setDifficulty(2);Game.newGame();}
        }
    }


    MainFrame() throws HeadlessException {
        setTitle("Minesweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new MainPanel();
        add(panel, BorderLayout.NORTH);

        JMenu fileMenu = new JMenu("Game");
        fileMenu.add(new MainFrame.TestAction("New"));

        Action easy = new MainFrame.TestAction("Easy");
        Action normal = new MainFrame.TestAction("Normal");
        Action hard = new MainFrame.TestAction("Hard");

        JMenu editMenu = new JMenu("Difficulty");
        editMenu.add(easy);
        editMenu.add(normal);
        editMenu.add(hard);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);


        popup = new JPopupMenu();
        popup.add(easy);
        popup.add(normal);
        popup.add(hard);

        JPanel panel = new JPanel();
        panel.setComponentPopupMenu(popup);
        add(panel);

        panel.addMouseListener(new MouseAdapter() {});

    }

    public static Canvas getCanvas() {
        return canvas;
    }

    void mineSweeper() {
        canvas = new Canvas();
        add(canvas);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
