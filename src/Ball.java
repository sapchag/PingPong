import java.awt.*;
import java.util.Random;

public class Ball extends GameObject {

    static public final int radius = 15;

    public float getSpeed() {
        return (float) Math.sqrt((speedX * speedX) + (speedY * speedY));
    }

    public Ball setSpeed(float speed) {
        float ratio = speed / getSpeed();
        speedX = ratio * speedX;
        speedY = ratio * speedY;
        return this;
    }

    public Ball setXYSpeed(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
        return this;
    }

    @Override
    Ball move() {

        speedLimit();
        super.move();


        return this;
    }

    public Ball setXYSpeed(double speedX, double speedY) {
        setXYSpeed((float) speedX, (float) speedY);
        return this;
    }

    void speedLimit() {
        if (getSpeed() > 2 * radius) {
            setSpeed(2 * radius);
        }
        if (getSpeed() < 5) {
            setSpeed(5);
        }
    }

    public Ball setRandomSpeed() {
        Random r = new Random();
        speedX = r.nextFloat() * 10 - 5;
        if (Math.abs(speedX) < 3) {
            speedX = 3 / Math.abs(speedX) * speedX;
        }
        speedY = r.nextFloat() * 10 - 5;
        setSpeed(7);
        return this;
    }

    @Override
    Ball draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
        g.setColor(Color.black);
//        g.drawLine(getX(), getY(), getX() + (int) speedX, getY() + (int) speedY);
        return this;
    }

    boolean isOverlapping(GameObject gameObject) {
        if ((Math.abs(this.getX() - gameObject.getX()) < this.width / 2 + gameObject.width / 2) &&
                (Math.abs(this.getY() - gameObject.getY()) < this.width / 2 + gameObject.height / 2)) {
            return true;
        }

        return false;
    }

}
