package game;

import java.awt.event.KeyEvent;

public class YourTank extends Tank{




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

            case KeyEvent.VK_F2:

                if (!this.live) {

                    this.live = true;

                    this.life = 100;

                    tc.rebirthNum2++;
                }

                break;

            case KeyEvent.VK_A:

                bL = true;

                break;

            case KeyEvent.VK_W:

                bU = true;

                break;

            case KeyEvent.VK_D:

                bR = true;

                break;

            case KeyEvent.VK_S:

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

            case KeyEvent.VK_A:

                bL = false;

                break;

            case KeyEvent.VK_W:

                bU = false;

                break;

            case KeyEvent.VK_D:

                bR = false;

                break;

            case KeyEvent.VK_S:

                bD = false;

                break;

            case KeyEvent.VK_J:

                fire();

                break;

        }

        locateDirection();


    }






    public YourTank(int x, int y, boolean good, Direction dir, TankClient tc) {
        super(x, y, good, dir, tc);

    }

}
