import javax.swing.*;
import java.awt.*;

public class BallMovement extends Thread {
    JPanel p1;
    JPanel p2;
    JPanel ball;
    JPanel desk;
    JLabel[] scores;
    int ballDx = 5;
    int ballDy = 5;

    public BallMovement(JPanel ball, JPanel desk, JLabel[] scores, JPanel p1, JPanel p2) {
        this.scores = scores;
        this.ball = ball;
        this.desk = desk;
        this.p1 = p1;
        this.p2 = p2;
    }

    private void resetBall() {
        ball.setBounds(200, 200, 10, 10);
        ballDx = -ballDx; // Change direction after scoring
        ballDy = 5; // Reset to initial Y direction
    }

    public void run() {
        while (true) {
            Rectangle location = ball.getBounds();

            // Check for scoring
            if (location.x <= 40) {
                scores[1].setText((Integer.parseInt(scores[1].getText()) + 1) + "");
                resetBall();
                try {
                    Thread.sleep(1000); // Delay before resetting the ball
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue; // Skip the rest of the loop to apply the reset
            }
            if (location.x >= 450) {
                scores[0].setText((Integer.parseInt(scores[0].getText()) + 1) + "");
                resetBall();
                try {
                    Thread.sleep(1000); // Delay before resetting the ball
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue; // Skip the rest of the loop to apply the reset
            }

            // Move ball
            location.x += ballDx;
            location.y += ballDy;

            // Bounce off top and bottom edges
            if (location.y <= 0 || location.y >= desk.getHeight() - location.height-30) {
                ballDy = -ballDy;
            }

            // Bounce off paddles
            if (location.intersects(p1.getBounds())) {
                ballDx = Math.abs(ballDx);
                adjustBallDirection(location, p1.getBounds());
            }
            if (location.intersects(p2.getBounds())) {
                ballDx = -Math.abs(ballDx);
                adjustBallDirection(location, p2.getBounds());
            }

            ball.setBounds(location);
            desk.repaint();

            try {
                Thread.sleep(42); // Adjust for smoother animation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void adjustBallDirection(Rectangle ballLocation, Rectangle paddleBounds) {
        int hitPosition = ballLocation.y - paddleBounds.y;
        if (hitPosition < 3 * paddleBounds.height / 5) {
            ballDy = 5;
        } else if (hitPosition > 3 * paddleBounds.height / 3) {
            ballDy = -5;
        } else {
            ballDy = 0;
        }
    }
}
