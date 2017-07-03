import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    private static JLabel timeLabel = new JLabel();
    private static JButton button = new JButton("Начать заново?");
    private int i = 0;


    GameOverPanel() {
        setLayout(new GridLayout(1, 3));
        setBackground(new Color(51, 51, 51));

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Canvas.WIDTH / 4, Canvas.HEIGHT / 8);
    }
}