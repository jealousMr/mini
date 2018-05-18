package game;


import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Blood {

    int x, y, w, h;
    int step = 0;
    int i = 0;

    Timer timer = new Timer();

    TankClient tc;

    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            step++;
            i++;
            System.out.println("ok");
        }
    };



    private boolean live = true;

    // 指明血块运动的轨迹,由 pos 中各个点构成

    private int[][] pos = {

            { 350, 300 }, { 700, 300 }, { 100, 400 }, { 500, 50 }, { 30, 270 }, { 400, 55 }, { 340, 280 }

    };

    public Blood() {

        x = pos[0][0];

        y = pos[0][1];

        w = h = 15;


    }

    public void draw(Graphics g) {

        move();

        if (!live)
            return;

        Color c = g.getColor();

        g.setColor(new Color(0xf3715c));

        g.fillRect(x, y, w, h);

        g.setColor(c);



    }

    private void move() {

        if (!live) {
            step++;
            live = true;
        }

        if (step == pos.length) {

            step = 0;

        }

        x = pos[step][0];
        y = pos[step][1];

    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);

    }

    public boolean isLive() {

        return live;

    }

    public void setLive(boolean live) {

        this.live = live;

    }







}
