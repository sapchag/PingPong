import java.awt.*;

public class Player extends GameObject {

    protected boolean isUp = false;
    protected boolean isDown = false;

    protected final float accelerationBase = Ball.radius / 9f;

    Player() {
        friction = 0.10f;
        powerLoss = 0.3f;
        width = Ball.radius * 1;
        height = Ball.radius * 6;
    }

    @Override
    Player move() {

        acceleration = 0;

        if (isUp && !isDown) {
            acceleration = -accelerationBase;
        }

        if (isDown && !isUp) {
            acceleration = accelerationBase;
        }

        super.move();

        return this;
    }

    @Override
    Player draw(Graphics g) {
        g.setColor(Color.BLUE);
//        g.fillRect(getX() - width / 2, getY() - height / 2 + width / 2, width, height - width / 2);
//        g.fillOval(getX() - width / 2, getY() - height / 2 , width, width);
//        g.fillOval(getX() - width / 2, getY() + height / 2 - width / 2, width, width);

//        g.drawRect(getX() - width / 2, getY() - height / 2 , width, height );
        g.fillRect(getX() - width / 2, getY() - height / 2 + width / 2, width, height - width);
        g.fillOval(getX() - width / 2, getY() - height / 2, width, width);
        g.fillOval(getX() - width / 2, getY() + height / 2 - width, width, width);

//        g.setColor(Color.YELLOW);
//        g.fillOval(getX() - 2, getY() - height / 2+width/2-2, 4, 4);
//        g.fillOval(getX() - 2, getY() + height / 2-width/2-2, 4, 4);
        return this;
    }

    public void setUpAcceleration(boolean up) {
        isUp = up;
    }

    public void setDownAcceleration(boolean down) {
        isDown = down;
    }

}
