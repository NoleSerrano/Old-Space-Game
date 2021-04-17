
import javafx.scene.shape.Ellipse;
import javax.swing.Timer;

public class Enemy {

    public double x;
    public double y;
    private int size;
    private int health = 25;
    //initially mult was at 1.5
    private double multiplier = 3;
    private Timer t;

    //HAVE IT SO MAYBE EVERY 10TH MOVE IT SHOOTS PLASMA INSTEAD OF HAVING AN 
    //INTERNAL TIMER TO SHOOT AND FUCK THINGS UP
    //ALSO MAYBE ADD SOMETHING LIKE LASER HEALTH SO A LASER GETS LIKE 2-3 SHOTS
    //FOR SHOOTING I COULD ALSO HAVE A BOOLEAN SHOOT AND IT'S TRUE AFTER A CERTAIN AMOUNT OF
    //TIME AND IT SHOOTS A PLASMA IN MAIN NOT SHOOTING A PLASMA HERE
    public Enemy(int randomx, int randomy, int length) {
        //adding length/2 to cords makes the cords the center of the object
        x = randomx + length / 2;
        y = randomy + length / 2;
        size = length;
    }

    //player and enemy cords are in the center
    public void move(Player p) {
        double angle = Math.atan2((p.y - y), (p.x - x));
        x += multiplier * Math.cos(angle);
        y += multiplier * Math.sin(angle);
    }

    //returns top left pixel of enemy
    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getSize() {
        return size;
    }

    public void decHealth() {
        health--;
    }

    public int getHealth() {
        return health;
    }

    public Ellipse getShape() {
        //returns the shape of enemy, (in this case: circle)
        return new Ellipse(x, y, 15, 15);
    }
}
