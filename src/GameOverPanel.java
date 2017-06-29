import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    static JLabel timeLabel = new JLabel();
    JButton button = new JButton("Начать заново?");
    int i = 0;


    GameOverPanel() {
        setLayout(new GridLayout(1, 3));
        setBackground(new Color(51, 51, 51));

        timeLabel.setVisible(true);
        timeLabel.setFont(new Font("Comic Sans", Font.BOLD, 36));
        timeLabel.setBackground(Color.WHITE);
        button.addActionListener((e) -> {
            MainFrame.t.interrupt();
            Game.frame.dispose();
            Game.frame = new MainFrame();
            Game.frame.mineSweeper();
        });
        add(new JLabel("Lab"));
        add(button);
        add(timeLabel);
        repaint();
    }

    public void setTime(){
        timeLabel.setText(i + "");
        i++;
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Canvas.WIDTH / 4, Canvas.HEIGHT / 8);
    }
}