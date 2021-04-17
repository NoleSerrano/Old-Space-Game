
public class Laser {

    private double x1;
    private double y1;
    public double x2;
    public double y2;
    private final double angle;
    private final double multiplier = 14.6;
    private int moves = 0;

    public Laser(Player p) {
        angle = Math.atan2((p.ay - p.y), (p.ax - p.x));
        x1 = p.x;
        y1 = p.y;
        x2 = x1 + 50 * Math.cos(angle);
        y2 = y1 + 50 * Math.sin(angle);
    }

    public void Move() {
        x1 += multiplier * Math.cos(angle);
        x2 += multiplier * Math.cos(angle);
        y1 += multiplier * Math.sin(angle);
        y2 += multiplier * Math.sin(angle);
        moves++;
    }

    public int getX1() {
        return (int) x1;
    }

    public int getX2() {
        return (int) x2;
    }

    public int getY1() {
        return (int) y1;
    }

    public int getY2() {
        return (int) y2;
    }

    public int getMoves() {
        return moves;
    }

}
