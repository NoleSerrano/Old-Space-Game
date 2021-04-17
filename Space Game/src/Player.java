
import java.awt.geom.AffineTransform;

public class Player {

    public double x;
    public double y;
    public double ax;
    public double ay;
    private double bx;
    private double by;
    private double cx;
    private double cy;
    private double rads = .11;
    private double multiplier = 7.3;
    private double angle;

    public Player(int centx, int centy) {
        x = centx;
        y = centy;
        ax = centx;
        ay = centy - 13;
        bx = centx + 13 * Math.sqrt(3) / 2;
        by = centy + 13 / 2;
        cx = centx - 13 * Math.sqrt(3) / 2;
        cy = centy + 13 / 2;
        angle = Math.atan2((ay - y), (ax - x));
    }

    public void rotateRight() {
        double[] pta = {ax, ay};
        AffineTransform.getRotateInstance(rads, x, y)
                .transform(pta, 0, pta, 0, 1);
        ax = pta[0];
        ay = pta[1];

        double[] ptb = {bx, by};
        AffineTransform.getRotateInstance(rads, x, y)
                .transform(ptb, 0, ptb, 0, 1);
        bx = ptb[0];
        by = ptb[1];

        double[] ptc = {cx, cy};
        AffineTransform.getRotateInstance(rads, x, y)
                .transform(ptc, 0, ptc, 0, 1);
        cx = ptc[0];
        cy = ptc[1];
    }

    public void rotateLeft() {
        double[] pta = {ax, ay};
        AffineTransform.getRotateInstance(-rads, x, y)
                .transform(pta, 0, pta, 0, 1);
        ax = pta[0];
        ay = pta[1];

        double[] ptb = {bx, by};
        AffineTransform.getRotateInstance(-rads, x, y)
                .transform(ptb, 0, ptb, 0, 1);
        bx = ptb[0];
        by = ptb[1];

        double[] ptc = {cx, cy};
        AffineTransform.getRotateInstance(-rads, x, y)
                .transform(ptc, 0, ptc, 0, 1);
        cx = ptc[0];
        cy = ptc[1];
    }

    public void move() {
        angle = Math.atan2((ay - y), (ax - x));
        x += multiplier * Math.cos(angle);
        ax += multiplier * Math.cos(angle);
        bx += multiplier * Math.cos(angle);
        cx += multiplier * Math.cos(angle);
        y += multiplier * Math.sin(angle);
        ay += multiplier * Math.sin(angle);
        by += multiplier * Math.sin(angle);
        cy += multiplier * Math.sin(angle);
    }

    public int getAX() {
        return (int) ax;
    }

    public int getAY() {
        return (int) ay;
    }

    public int getBX() {
        return (int) bx;
    }

    public int getBY() {
        return (int) by;
    }

    public int getCX() {
        return (int) cx;
    }

    public int getCY() {
        return (int) cy;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}
