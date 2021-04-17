
//plan make a spaceship that rotates
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends javax.swing.JFrame implements KeyListener {

    private final Timer t1;
    private final Timer t2;
    private final Timer t3;

    //images
    private final Image spaceimage;
    private final Image enemy1;

    private Player player;
    private final ArrayList<Laser> Lasers;
    private ArrayList<Enemy> Enemies;
    private boolean right;
    private boolean left;
    private boolean move;
    private boolean shoot;
    private boolean gameover;
    private int health = 1000;
    private int maxHealth = health;
    private int maxAmmo = 150;
    private int ammo = maxAmmo;
    private int score = 0;
    private Random r = new Random();

    //change resolution here (1920x1080)
    private final int maxX = 1920;
    private final int maxY = 1080;

    public Game() {

        this.setUndecorated(true);
        initComponents();
        setResizable(false);

        getContentPane().setBackground(Color.BLACK);
        this.setBounds(0, 0, maxX, maxY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createBufferStrategy(2);

        addKeyListener(this);

        //removes cursor
        this.getContentPane().setCursor(Toolkit.getDefaultToolkit().
                createCustomCursor(new BufferedImage(16, 16,
                        BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));

        //images
        ClassLoader cloader = Game.class.getClassLoader();
        spaceimage = Toolkit.getDefaultToolkit().getImage(cloader.getResource("space1.png"));
        prepareImage(spaceimage, this);
        enemy1 = Toolkit.getDefaultToolkit().getImage(cloader.getResource("enem1.png"));
        prepareImage(enemy1, this);

        //initializes arraylists
        Lasers = new ArrayList();
        Enemies = new ArrayList();

        //initializes player, enemies, asteroids
        player = new Player(maxX / 2, maxY / 2);
        addEnemies(100);

        //timers
        ActionListener task1 = this::doAction;
        t1 = new Timer(20, task1);
        t1.start();

        ActionListener task2 = this::doEnding;
        t2 = new Timer(20, task2);
        t2.start();

        ActionListener task3 = this::spawnRock;
        t3 = new Timer(5000, task3);
        t3.start();
    }

    private void doAction(ActionEvent e) {
        paintGame();
        updatePlayer();
        if (!Enemies.isEmpty()) {
            updateEnemies();
        }
        if (!Lasers.isEmpty()) {
            moveLasers();
        }
    }

    private void doEnding(ActionEvent e) {
        paintGame();
    }

    private void spawnRock(ActionEvent e) {

    }

    public void updatePlayer() {
        //updates postion and lasers of the player
        if (move) {
            player.move();
        }
        if (right && !left) {
            player.rotateRight();
        } else if (left && !right) {
            player.rotateLeft();
        }
        if (shoot && ammo > 0) {
            ammo--;
            Lasers.add(new Laser(player));
        } else if (!shoot && ammo < maxAmmo - 4) {
            ammo += 2;
        } else if (!shoot) {
            ammo = maxAmmo;
        }
        if (player.getX() < 0 || player.getX() > maxX || player.getY() < 0
                || player.getY() > maxY) {
            health -= 6;
        }
//        else if (health < maxHealth) {
//            health++;
//        }
        if (health < 0) {
            gameover = true;
            t1.stop();
            t2.start();
        }
    }

    public void updateEnemies() {
        for (int i = Enemies.size() - 1; i >= 0; i--) {
            Enemy E = Enemies.get(i);
            if (distance(E.getX(), E.getY()) > 100) {
                E.move(player);
            }
            //sees if lasers hit enemy and if enemy's health is less than or equal
            //to zero then it gets removed from the arraylist
            if (!Lasers.isEmpty()) {
                boolean alive = true;
                int j = Lasers.size() - 1;
                while (alive && j >= 0) {
                    Laser L = Lasers.get(j);
                    j--;
                    if (E.getShape().contains(L.x2, L.y2)) {
                        Lasers.remove(L);
                        score++;
                        E.decHealth();
                        if (E.getHealth() <= 0) {
                            Enemies.remove(E);
                            alive = false;
                        }
                    }
                }
            }
        }
    }

    public void addEnemies(int n) {
        for (int i = 0; i < n; i++) {
            int x;
            int y;
            //makes sure the enemy doesn't spawn near the player
            do {
                x = r.nextInt(maxX);
                y = r.nextInt(maxY);
            } while (distance(x, y) < 150);
            Enemies.add(new Enemy(x, y, 30));
        }
    }

    public double distance(int x, int y) {
        //returns distance between player and two points
        return Math.sqrt(Math.pow(player.y - y, 2) + Math.pow(player.x - x, 2));
    }

    public void moveLasers() {
        for (int i = Lasers.size() - 1; i >= 0; i--) {
            Laser L = Lasers.get(i);
            if (L.getMoves() < 200) {
                L.Move();
            } else {
                Lasers.remove(i);
            }
        }
    }

    public void restart() {
        t2.stop();
        t1.start();
        gameover = false;
        Lasers.clear();
        Enemies.clear();
        player = new Player(maxX / 2, maxY / 2);
        addEnemies(5);
        health = maxHealth;
        score = 0;
        ammo = maxAmmo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    public void paintGame() {

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {
            g = bf.getDrawGraphics();
            //not gameover
            if (!gameover) {
                g.drawImage(spaceimage, 0, 0, maxX, maxY, null);

                //draws enemies
                for (Enemy E : Enemies) {
                    g.drawImage(enemy1, E.getX() - E.getSize() / 2, E.getY() - E.getSize() / 2,
                            E.getSize(), E.getSize(), null);
                }

                //draws player
                Polygon p = new Polygon();
                p.addPoint(player.getAX(), player.getAY());
                p.addPoint(player.getBX(), player.getBY());
                p.addPoint(player.getCX(), player.getCY());
                g.setColor(Color.LIGHT_GRAY);
                g.fillPolygon(p);

                g.setColor(Color.red);
                for (Laser L : Lasers) {
                    g.drawLine(L.getX1(), L.getY1(), L.getX2(), L.getY2());
                }

                //player's health and ammo bar
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect((maxX - 450) / 2, maxY - 80, 450, 25);
                g.fillRect((maxX - 270) / 2, maxY - 51, 270, 17);
                g.setColor(new Color(0, 51, 0));
                g.fillRect((maxX - 446) / 2, maxY - 78, 446, 21);
                g.setColor(new Color(128, 255, 0));
                g.fillRect((maxX - 446) / 2, maxY - 78, 446 * health / maxHealth, 21);
                g.setColor(new Color(51, 0, 0));
                g.fillRect((maxX - 266) / 2, maxY - 49, 266, 13);
                g.setColor(Color.red);
                g.fillRect((maxX - 266) / 2, maxY - 49, 266 * ammo / maxAmmo, 13);

                //score
                g.setColor(Color.yellow);
                String s = "Score: " + score;
                g.setFont(new Font("Impact", 1, 20));
                g.drawString(s, (maxX - s.length() * 10) / 2, maxY - 85);

            } else {
                g.setColor(Color.black);
                g.fillRect(0, 0, maxX, maxY);
            }
        } finally {
            g.dispose();
        }
        // Shows the contents of the backbuffer on the screen.
        bf.show();

        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        //Drawing is done which looks very jerky
        Toolkit.getDefaultToolkit().sync();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            right = true;
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            left = true;
        } else if (key == KeyEvent.VK_R) {
            restart();
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            move = true;
        } else if (key == KeyEvent.VK_SPACE) {
            shoot = true;
        } else if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            right = false;
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            left = false;
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            move = false;
        } else if (key == KeyEvent.VK_SPACE) {
            shoot = false;
        }
    }
}
