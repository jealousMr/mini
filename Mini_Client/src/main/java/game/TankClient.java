package game;




import java.awt.*;

import java.awt.event.*;

import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.util.ArrayList;



public class TankClient extends Frame {


//	public ImageIcon a = new ImageIcon("images/tank.jpg");


    public Toolkit t = Toolkit.getDefaultToolkit();
    public Image image = t.getImage("C:\\my\\projectText\\Mini_Client\\src\\main\\java\\GUI_img\\LoginPage_img\\tank.jpg");

    public int scores = 10;
    public int num ;
    public int rebirthNum1 = 0;
    public int rebirthNum2 = 0;
    public boolean j ;
    public boolean boss = false;
    protected static Random rr = new Random();
    public boolean flag = true;
    public static final int GAME_WIDTH = 800;

    public static final int GAME_HEIGHT = 600;

    Tank yourTank = new YourTank(500, 500, true,Tank.Direction.STOP, this);
    Tank myTank = new MyTank(750, 500, true, Tank.Direction.STOP, this);

    Wall w1 = new Wall(400, 200, 20, 150, this), w2 = new Wall(300, 100, 300, 20, this), w3 = new Wall(550, 500, 200, 20, this);

    List<Explode> explodes = new ArrayList<Explode>();

    List<Missile> missiles = new ArrayList<Missile>();

    List<Tank> tanks = new ArrayList<Tank>();

    Image offScreenImage = null;

    Blood b = new Blood();

    public void paint(Graphics g) {

        /*
         *
         * 指明子弹 -爆炸 -坦克的数量
         *
         * 以及坦克的生命值
         *
         */

        g.drawString("Scores:" + (scores-(num = tanks.size())), 10, 50);

        g.drawString("Tank1 Rebirth:" + rebirthNum1, 10, 70);

        g.drawString("Tank2 Rebirth:" + rebirthNum2, 10, 90);

        g.drawString("tanks count:" + tanks.size(), 10, 110);

        g.drawString("Tank1 life:" + myTank.getLife(), 10, 130);

        g.drawString("Tank2 life"+ yourTank.getLife(), 10,150);

        if (tanks.size() <= 0) {
            boss = true;
            scores += 5;
            num += 5;
            for (int i = 0; i < 5; i++) {

                tanks.add(new Tank(rr.nextInt(800), rr.nextInt(600), false, Tank.Direction.D, this));
            }
        }

        for (int i = 0; i < missiles.size(); i++) {

            Missile m = missiles.get(i);

            m.hitTanks(tanks);

            m.hitTank(myTank);

            m.hitTank(yourTank);

            m.hitWall(w1);

            m.hitWall(w2);

            m.hitWall(w3);

            m.draw(g);

            // if(!m.isLive()) missiles.remove(m);

            // else m.draw(g);

        }

        for (int i = 0; i < explodes.size(); i++) {

            Explode e = explodes.get(i);

            e.draw(g);

        }

        for (int i = 0; i < tanks.size(); i++) {

            Tank t = tanks.get(i);

            t.collidesWithWall(w1);

            t.collidesWithWall(w2);

            t.collidesWithWall(w3);

            t.collidesWithTanks(tanks);

            t.draw(g);

        }

        yourTank.collidesWithWall(w1);
        yourTank.collidesWithWall(w2);
        yourTank.collidesWithWall(w3);
        myTank.collidesWithWall(w1);
        myTank.collidesWithWall(w1);
        myTank.collidesWithWall(w1);

        if (j) {
            myTank.draw(g);
        }


        myTank.eat(b);

        yourTank.draw(g);

        yourTank.eat(b);

        w1.draw(g);

        w2.draw(g);

        w3.draw(g);

        b.draw(g);

    }

    public void update(Graphics g) {

        if (offScreenImage == null) {

            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }

        Graphics gOffScreen = offScreenImage.getGraphics();

        Color c = gOffScreen.getColor();

        gOffScreen.setColor(new Color(199,212,247));

        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        gOffScreen.setColor(c);

        paint(gOffScreen);

        g.drawImage(offScreenImage, 0, 0, null);
    }






    public void lauchFrame() {

        for (int i = 0; i < 10; i++) {

            tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Tank.Direction.D, this));
        }

        // this.setLocation(400, 300);

        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.setTitle("TankWar");

        this.setIconImage(image);

        this.setLocationRelativeTo(null);

//		this.addWindowListener(new WindowAdapter() {
//
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//
//		});
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                (e.getWindow()).dispose();
            }
        });



        this.setResizable(false);

        this.setBackground(new Color(199,212,247));

        this.addKeyListener(new KeyMonitor());

        this.setVisible(true);

        new Thread(new PaintThread()).start();


    }



    private class PaintThread implements Runnable {

        public void run() {


            while (true) {

                repaint();

                try {

                    Thread.sleep(50);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

        }

    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
            yourTank.keyReleased(e);

        }

        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
            yourTank.keyPressed(e);

        }

    }

}