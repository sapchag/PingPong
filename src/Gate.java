import java.awt.*;

public class Gate extends GameObject {

    int goals=0;

    public Gate() {
        width = Ball.radius  / 2;
        height = Ball.radius * 10;
    }

    @Override
    Gate draw(Graphics g) {
        g.setColor(Color.BLACK);
//        g.fillRect(getX() - width / 2, getY() - height / 2 + width / 2, width, height - width / 2);
//        g.fillOval(getX() - width / 2, getY() - height / 2 , width, width);
//        g.fillOval(getX() - width / 2, getY() + height / 2 - width / 2, width, width);

        g.drawRect(getX() - width / 2, getY() - height / 2, width, height);
//        g.fillRect(getX() - width / 2, getY() - height / 2 + width / 2, width, height - width);
//        g.fillOval(getX() - width / 2, getY() - height / 2, width, width);
//        g.fillOval(getX() - width / 2, getY() + height / 2 - width, width, width);

//        g.setColor(Color.YELLOW);
//        g.fillOval(getX() - 2, getY() - height / 2+width/2-2, 4, 4);
//        g.fillOval(getX() - 2, getY() + height / 2-width/2-2, 4, 4);
        return this;
    }

    public int getGoals() {
        return goals;
    }

    public Gate addGoals() {
        goals ++;
        return this;
    }
}
