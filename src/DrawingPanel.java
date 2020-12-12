import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.lang.Math.*;

class DrawingPanel extends JPanel implements KeyListener {
    Ball ball = new Ball().setRandomSpeed();
    Player leftPlayer = new Player();
    Player rightPlayer = new Player();

    Gate leftGate = new Gate();
    Gate rightGate = new Gate();

    boolean isGoal = false;


    int delta = Ball.radius * 4;
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    ArrayList<GameObject> players = new ArrayList<GameObject>();
    Timer timer = new Timer(10, null);
    Timer timerPlayer = new Timer(50, null);
    String ballMessage;

    public DrawingPanel() {

        this.addKeyListener(this);
        this.setFocusable(true);

        players.add(leftPlayer);
        players.add(rightPlayer);
        ActionListener performerPlayer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPlayer.move();
                checkWindowBorder(rightPlayer);
                leftPlayer.move();
                checkWindowBorder(leftPlayer);
            }
        };

        ActionListener performer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGoal) {
                    timer.stop();
                    timerPlayer.stop();
                    return;
                }
                ball.move();
                checkPlayers();
                checkWindowBorder(ball);
                repaint();
            }
        };

        timer.addActionListener(performer);
        timerPlayer.addActionListener(performerPlayer);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 38) {
            rightPlayer.setUpAcceleration(true);
        }
        if (e.getKeyCode() == 40) {
            rightPlayer.setDownAcceleration(true);
        }
        if (e.getKeyCode() == 87) {
            leftPlayer.setUpAcceleration(true);
        }
        if (e.getKeyCode() == 83) {
            leftPlayer.setDownAcceleration(true);
        }

        if (e.getKeyCode() == 32) {
            if (isGoal) {
                toCentrAll();
                isGoal = false;
                ball.setRandomSpeed();
                timer.start();
                timerPlayer.start();
                return;
            }

            if (!timer.isRunning()) {
                timer.start();
                timerPlayer.start();
            } else {
                timerPlayer.stop();
                timer.stop();
            }
        }

    }

    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == 38) {
            rightPlayer.setUpAcceleration(false);
        }
        if (e.getKeyCode() == 40) {
            rightPlayer.setDownAcceleration(false);
        }
        if (e.getKeyCode() == 87) {
            leftPlayer.setUpAcceleration(false);
        }
        if (e.getKeyCode() == 83) {
            leftPlayer.setDownAcceleration(false);
        }
    }

    void checkPlayers() {
        for (GameObject it : players) {
            if (ball.isOverlapping(it)) {
                if (abs(ball.getFloatY() - it.getFloatY()) > it.getHeight() / 2 - ball.getWidht() / 4) {
                    float distance = 0;
                    float dX = ball.getFloatX() - it.getFloatX();
                    float dY = abs(ball.getFloatY() - it.getFloatY()) - it.getHeight() / 2 + it.getWidht() / 2;
                    distance = (float) sqrt(dX * dX + dY * dY);
                    if (distance > ball.width / 2 + it.width / 2) continue;
                    if (ball.getFloatY() < it.getFloatY()) dY = -dY;
                    double vectorAngle = toDegrees(atan2(dY, dX));
                    double ballAngle = toDegrees(atan2((ball.getSpeedY()), (ball.getSpeedX())));
                    double tensionSpeed = abs(ball.getSpeed() * cos(toRadians(vectorAngle - ballAngle)));
                    double tensionSpeedX = tensionSpeed * cos(toRadians(vectorAngle)) * 2;
                    double tensionSpeedY = tensionSpeed * sin(toRadians(vectorAngle)) * 2;
                    double resultSpeedX = ball.speedX + tensionSpeedX;
                    double resultSpeedY = ball.speedY + tensionSpeedY;


                    ball.setXYSpeed(resultSpeedX, resultSpeedY + it.speedY / 2);
                    ball.move();


                } else {
                    if ((ball.getFloatX() < it.getFloatX() && ball.getSpeedX() > 0) ||
                            (ball.getFloatX() > it.getFloatX() && ball.getSpeedX() < 0)) {
                        ball.setSpeedX(ball.getSpeedX() * -1);
                        ball.setSpeedY(ball.getSpeedY() + it.speedY * 0.2f);
                    }
                }
            }
        }
    }

    void checkWindowBorder(GameObject it) {


        if (it.getX() < it.getWidht() / 2 & it.getSpeedX() < 0) {
            if (it.getY() < leftGate.getY() - leftGate.getHeight() / 2 + it.getHeight() / 3 ||
                    it.getY() > leftGate.getY() + leftGate.getHeight() / 2 - it.getHeight() / 3) {
                it.setSpeedX(it.getSpeedX() * -1);
            } else if (it.getX() < 0) {
                isGoal = true;
                rightGate.addGoals();
            }
            it.lossPower();
        }

        if (it.getY() < it.getHeight() / 2 & it.getSpeedY() < 0) {
            it.setSpeedY(it.getSpeedY() * -1);
            it.lossPower();
        }

        if (it.getX() > getWidth() - it.getWidht() / 2 & it.getSpeedX() > 0) {
            if (it.getY() < rightGate.getY() - rightGate.getHeight() / 2 + it.getHeight() / 3 ||
                    it.getY() > rightGate.getY() + rightGate.getHeight() / 2 - it.getHeight() / 3) {
                it.setSpeedX(it.getSpeedX() * -1);
            } else if (it.getX() > getWidth()) {
                isGoal = true;
                leftGate.addGoals();
            }
            it.lossPower();
        }

        if (it.getY() > getHeight() - it.getHeight() / 2 & it.getSpeedY() > 0) {
            it.setSpeedY(it.getSpeedY() * -1);
            it.lossPower();
        }

    }

    @Override
    public Dimension getPreferredSize() {
        int width = 800;
        int height = 500;

        ball.setX(width / 2).setY(height / 2);
        leftPlayer.setX(delta).setY(height / 2);
        rightPlayer.setX(width - delta).setY(height / 2);

        leftGate.setX(leftGate.getWidht() / 2);
        leftGate.setY(height / 2);
        rightGate.setX(width - rightGate.getWidht());
        rightGate.setY(height / 2);
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        leftPlayer.draw(g);
        rightPlayer.setX(g.getClipBounds().width - delta);
        rightPlayer.draw(g);


        leftGate.setY(g.getClipBounds().height / 2);
        leftGate.draw(g);

        rightGate.setX(g.getClipBounds().width - rightGate.getWidht() / 2);
        rightGate.setY(g.getClipBounds().height / 2);
        rightGate.draw(g);
        ball.draw(g);


        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(50f);
        g.setFont(newFont);
        g.setColor(Color.GRAY);
        ballMessage = String.valueOf(leftGate.getGoals());
        //g.setFont(new Font().deriveFont(20));
        g.drawString(ballMessage, getWidth() / 2 - 100, 50);
        ballMessage = String.valueOf(rightGate.getGoals());
        g.drawString(ballMessage, getWidth() / 2 + 100, 50);
        ballMessage = ":";
        g.drawString(ballMessage, getWidth() / 2, 50);


        //int diametr = Math.min(g.getClipBounds().width, g.getClipBounds().height);
        //g.fillOval(0, 0, diametr, diametr);
    }

    void toCentrAll() {
        ball.setX(getWidth() / 2);
        ball.setY(getHeight() / 2);
        rightPlayer.setY(getHeight() / 2);
        rightPlayer.setSpeedY(0);
        leftPlayer.setY(getHeight() / 2);
        leftPlayer.setSpeedY(0);
        repaint();
    }

}