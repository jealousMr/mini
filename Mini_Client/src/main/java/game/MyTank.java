package game;

import java.awt.event.KeyEvent;

public class MyTank extends Tank{

    @Override
    public Missile fire() {
        if (!live)
            return null;

        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;

        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y, good, ptDir, this.tc);

        tc.missiles.add(m);

        return m;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_F1:

                if (!this.live) {

                    this.live = true;

                    this.life = 100;

                    tc.rebirthNum1++;
                }

                break;

            case KeyEvent.VK_LEFT:

                bL = true;

                break;

            case KeyEvent.VK_UP:

                bU = true;

                break;

            case KeyEvent.VK_RIGHT:

                bR = true;

                break;

            case KeyEvent.VK_DOWN:

                bD = true;

                break;

        }

        locateDirection();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {

//		case KeyEvent.VK_CONTROL:
//
//			fire();
//
//			break;

            case KeyEvent.VK_LEFT:

                bL = false;

                break;

            case KeyEvent.VK_UP:

                bU = false;

                break;

            case KeyEvent.VK_RIGHT:

                bR = false;

                break;

            case KeyEvent.VK_DOWN:

                bD = false;

                break;

            case KeyEvent.VK_NUMPAD5:

                fire();

                break;

        }

        locateDirection();
    }

    public MyTank(int x, int y, boolean good, Direction dir, TankClient tc) {
        super(x, y, good, dir, tc);





    }

}
