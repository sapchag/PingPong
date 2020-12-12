import java.awt.*;

public class GameObject {
    float x = 0;
    float y = 0;
    float speedX = 0;
    float speedY = 0;
    float friction = 0.001f;
    protected float acceleration = 0;
    protected float powerLoss = 0;
    protected int width = Ball.radius * 2;
    protected int height = Ball.radius * 2;

    GameObject draw(Graphics g) {
        return this;
    }

    GameObject move() {
        x = x + speedX;
        y = y + speedY;

        if (acceleration == 0) {
            speedX *= (1 - friction);
            speedY *= (1 - friction);
        } else {
            speedY += acceleration;
        }
        return this;
    }

    public int getX() {
        return Math.round(x);
    }

    public float getFloatX() {
        return x;
    }

    public GameObject setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return Math.round(y);
    }

    public float getFloatY() {
        return y;
    }


    public GameObject setY(int y) {
        this.y = y;
        return this;
    }

    public float getSpeed() {
        return (float) Math.sqrt((double) (speedX * speedX + speedY * speedY));
    }

    public float getSpeedX() {
        return speedX;
    }

    public GameObject setSpeedX(float speedX) {
        this.speedX = speedX;
        return this;
    }

    public float getSpeedY() {
        return speedY;
    }

    public GameObject setSpeedY(float speedY) {
        this.speedY = speedY;
        return this;
    }

    public int getWidht() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameObject lossPower() {
        speedX *= (1 - powerLoss);
        speedY *= (1 - powerLoss);
        return this;
    }
}
