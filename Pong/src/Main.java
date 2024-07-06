import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class PongPanel extends JFrame implements KeyListener {
    JPanel p1;
    JPanel p2;
    JLabel[] scores = new JLabel[2];
    JPanel ball = new JPanel();
    JPanel desk = new JPanel();
    BallMovement movement;

    public PongPanel(JPanel p1, JPanel p2) {
        super("Pong");
        this.p1 = p1;
        this.p2 = p2;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        desk.setLayout(null);
        desk.setBackground(Color.black);
        desk.setBounds(0, 0, 500, 500);
        p1.setBounds(50, 200, 10, 50);
        p2.setBounds(440, 200, 10, 50);
        add(desk);
        desk.add(p1);
        desk.add(p2);
        desk.add(ball);
        ball.setBounds(200, 200, 10, 10);
        scores[0] = new JLabel();
        scores[0].setBounds(125, 10, 50, 50);
        scores[0].setForeground(Color.WHITE);
        scores[1] = new JLabel();
        scores[1].setBounds(375, 10, 50, 50);
        scores[1].setForeground(Color.WHITE);
        scores[0].setText("0");
        scores[1].setText("0");
        desk.add(scores[0]);
        desk.add(scores[1]);
        addKeyListener(this);
        setSize(500, 500);
        setVisible(true);
        movement = new BallMovement(ball, desk, scores, p1, p2);
        movement.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            movePanel(p2, -5);
        } else if (key == KeyEvent.VK_DOWN) {
            movePanel(p2, 5);
        } else if (key == KeyEvent.VK_W) {
            movePanel(p1, -5);
        } else if (key == KeyEvent.VK_S) {
            movePanel(p1, 5);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void movePanel(JPanel panel, int y) {
        Rectangle bounds = panel.getBounds();
        int Y = bounds.y + y;
        if (Y >= 0 && Y <= desk.getHeight() - bounds.height) {
            panel.setBounds(bounds.x, Y, bounds.width, bounds.height);
            desk.repaint();
        }
    }

    public static void main(String[] args) {
        JPanel p1 = new JPanel();
        p1.setBackground(Color.WHITE);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.WHITE);
        new PongPanel(p1, p2);
    }
}
