import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class Canvas extends JPanel
{
    private ArrayList<Pair> options = new ArrayList<>();
    private Image mine = new ImageIcon("res/mine.png").getImage();
    private Image flag = new ImageIcon("res/flag.png").getImage();
    Mouse mouse = new Mouse();

    static  int WIDTH = Cell.SCALE * 9;
    static  int HEIGHT = Cell.SCALE * 9;
    private static int cellsX = WIDTH / Cell.SCALE;
    private static int cellsY = HEIGHT / Cell.SCALE;
    private static Cell[][] grid = new Cell[cellsX][cellsY];
    private static int mines = cellsX * cellsY / 5;

    //Audio files
    private static File pick = new File("res/pick.wav");
    private static File plock = new File("res/plock.wav");
    private static File explosion = new File("res/explosion.wav");
    private static AudioInputStream aispick;
    private static AudioInputStream aisplock;
    private static AudioInputStream aisExplosion;
    private static Clip clpick;
    private static Clip clplock;
    private static Clip clpExplosion;


    public static int getMines() {
        return mines;
    }

    public static void setDifficulty(int x){
        switch (x)
        {
            case 1 : {
                mines = 40;
                WIDTH = Cell.SCALE * 16;
                HEIGHT = Cell.SCALE * 16;
                cellsX = WIDTH / Cell.SCALE;
                cellsY = HEIGHT / Cell.SCALE;
                grid = new Cell[cellsX][cellsY];
                break;
            }
            case 2 : {
                mines = 99;
                WIDTH = Cell.SCALE * 30;
                HEIGHT = Cell.SCALE * 16;
                cellsX = WIDTH / Cell.SCALE;
                 cellsY = HEIGHT / Cell.SCALE;
                grid = new Cell[cellsX][cellsY];
                break;
            }
            default : {
                mines = 10;
                WIDTH = Cell.SCALE * 9;
                HEIGHT = Cell.SCALE * 9;
                int cellsX = WIDTH / Cell.SCALE;
                int cellsY = HEIGHT / Cell.SCALE;
                grid = new Cell[cellsX][cellsY];
                break;
            }
        }
    }

    {
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                options.add(new Pair(i, j));
            }
        }
    }

    static Cell[][] getGrid() {
        return grid;
    }

    Canvas() {
        addMouseListener(mouse);

        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        //Setting bombs...
        for (int n = 0; n < mines; n++) {
            int i = (int) Math.round(Math.random() * (options.size() - 1));
            Pair pair = options.get(i);
            options.remove(i);
            grid[pair.getA()][pair.getB()].setBee(true);
        }
    }

    public static int getCellsX() {
        return cellsX;
    }

    public static int getCellsY() {
        return cellsY;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(51, 51, 51));
        for (Cell[] c : grid) {
            for (Cell cell : c) {
                g.fill3DRect(cell.getX(), cell.getY(), Cell.SCALE, Cell.SCALE, true);
                if (cell.isRevealed()) {
                    if (cell.isBee()) {
                        g.fill3DRect(cell.getX(), cell.getY(), Cell.SCALE, Cell.SCALE, false);
                        g.drawImage(mine, cell.getX() + 1, cell.getY() + 1, Cell.SCALE - 2, Cell.SCALE - 2, null);
                    } else {
                        g.fill3DRect(cell.getX(), cell.getY(), Cell.SCALE, Cell.SCALE, false);

                        if (cell.getNeighbors() > 0) {
                            if (cell.getNeighbors() == 1)
                                g.setColor(new Color(0, 191, 255));
                            if (cell.getNeighbors() == 2)
                                g.setColor(new Color(28, 211, 162));
                            if (cell.getNeighbors() == 3)
                                g.setColor(new Color(196, 30, 58));
                            if (cell.getNeighbors() == 4)
                                g.setColor(new Color(255, 140, 105));
                            if (cell.getNeighbors() == 5)
                                g.setColor(new Color(153, 102, 204));
                            String s = cell.getNeighbors() + "";
                            Font f = new Font("PT Serif", Font.BOLD, 12);
                            g.setFont(f);
                            g.drawString(s, cell.getX() + Cell.SCALE / 2 - 4, cell.getY() + Cell.SCALE / 2 + 1);
                            g.setColor(new Color(51, 51, 51));
                        }
                    }
                }
                if (cell.isFlag()) {
                    g.drawImage(flag, cell.getX() + 1, cell.getY() + 1, Cell.SCALE - 2, Cell.SCALE - 2, null);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public Mouse getMouse() {
        return mouse;
    }

    public class Mouse extends MouseAdapter {
        public int flag = 0;
        @Override
        @SuppressWarnings("Duplicates")
        public void mouseReleased(MouseEvent e) {
            int count = 0;
            flag = 0;
            int x = e.getX();
            int y = e.getY();
            if (e.getButton() == 1) {
                for (Cell[] c : grid) {
                    for (Cell cell : c) {
                        if (cell.getX() < x && cell.getX() + Cell.SCALE > x && cell.getY() < y && cell.getY() + Cell.SCALE > y) {
                            cell.changeReveal();
                            if (cell.isBee()) {
                                try {
                                    aisExplosion = AudioSystem.getAudioInputStream(explosion);
                                    clpExplosion = AudioSystem.getClip();
                                    clpExplosion.open(aisExplosion);
                                    clpExplosion.start();
                                } catch (Exception ex) {
                                }
                                for (Cell[] c1 : grid)
                                    for (Cell cellOne : c1)
                                        cellOne.changeReveal();
                                repaint();
                                MainPanel.getT().cancel();
                                MainPanel.getM().cancel();
                            }
                            else {
                                try {
                                    aispick = AudioSystem.getAudioInputStream(pick);
                                    clpick = AudioSystem.getClip();
                                    clpick.open(aispick);
                                    clpick.start();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                }
            }
            if (e.getButton() == 3) {
                for (Cell[] c : grid) {
                    for (Cell cell : c) {
                        if (cell.getX() < x && cell.getX() + Cell.SCALE > x && cell.getY() < y && cell.getY() + Cell.SCALE > y)
                            cell.setFlag();

                    }
                }
                try {
                    aisplock = AudioSystem.getAudioInputStream(plock);
                    clplock = AudioSystem.getClip();
                    clplock.open(aisplock);
                    clplock.start();
                } catch (Exception ex) {
                }
            }
            for (Cell[] c : grid)
                for (Cell cell : c){
                    if (cell.isRevealed())
                        count++;
                    if(cell.isFlag())
                        flag++;
            }

                        if (count == cellsX * cellsY - mines)
                Game.gameWin();
            if (count == 1)
                MainPanel.beginSchedule();
            repaint();
        }

        public  int getFlag() {
            return flag;
        }
    }

    public class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }
}
