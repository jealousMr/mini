package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MyPanel extends JPanel{
    public Image img;
    public Toolkit t;

    public MyPanel() {
        t = Toolkit.getDefaultToolkit();
        img = t.getImage("C:\\my\\projectText\\Mini_Client\\src\\main\\java\\GUI_img\\LoginPage_img\\menu.png");

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
