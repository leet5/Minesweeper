import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class MainPanel extends JPanel{
    private static TimerTask task;
    private static Timer t;
    private static JLabel timeLabel = new JLabel();
    private static JLabel mineLabel = new JLabel();
    private static JButton button = new JButton("Restart");
    private static int counter = 0;

    MainPanel() {
        task = new MyTimerTask();
        t = new Timer(true);
        setBackground(new Color(51, 51, 51));
        setLayout(new GridLayout(1, 3));
        labelSet();

        add(timeLabel);
        add(button);
        add(mineLabel);
    }

    static Timer getT() {
        return t;
    }

    public static void setCounter(int counter) {
        MainPanel.counter = counter;
    }

    static void beginSchedule(){
        t.scheduleAtFixedRate(task, 0, 1000);
    }

    private void labelSet()
    {
        timeLabel.setText("Time:0");
        timeLabel.setName("0");
        Font font = new Font("monospaced", Font.PLAIN, Canvas.getCellsX() * 2 - 2);
        timeLabel.setFont(font);
        timeLabel.setForeground(new Color(255,255,255));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mineLabel.setText("Mines:" + Canvas.getMines());
        mineLabel.setName("0");
        Font font1 = new Font("monospaced", Font.PLAIN, Canvas.getCellsX() * 2 - 2);
        mineLabel.setFont(font1);
        mineLabel.setForeground(new Color(255,255,255));
        mineLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    static class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            mineLabel.setText("Mines:" + (Canvas.getMines() - MainFrame.getCanvas().getMouse().getFlag()));
            timeLabel.setText("Time:" + counter);
            counter++;
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Canvas.WIDTH / 4, Canvas.HEIGHT / 8);
    }
}