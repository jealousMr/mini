package game;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame implements ActionListener{
    public JButton Solo;
    public JButton Double;
    public JButton difficulty;
    public JPanel menu;
    public Graphics g;
    public TankClient tc;
    public JComboBox<String> box;

    public Toolkit t = Toolkit.getDefaultToolkit();
    public Image image = t.getImage("C:\\my\\projectText\\Mini_Client\\src\\main\\java\\GUI_img\\LoginPage_img\\tank.jpg");

    public static void main(String[] args) {
        Menu m =new Menu();
        m.iniFrame();
    }

    public void iniFrame() {
        Solo = new JButton("SOLO");
        Solo.addActionListener(this);
        Solo.setFont(new Font("ËÎÌו",Font.BOLD, 30));
        Solo.setBounds(300, 200, 150, 100);
        Solo.setBackground(new Color(173,255,47));


        Double = new JButton("DOUBLE");
        Double.addActionListener(this);
        Double.setFont(new Font("ËÎÌו",Font.BOLD, 30));
        Double.setBounds(300, 325, 150, 100);
        Double.setBackground(new Color(173,255,47));

        difficulty = new JButton("DIFFICULTY");
        difficulty.setBounds(300, 450, 150, 100);
        box = new JComboBox<String>();
        box.setFont(new Font("ËÎÌו",Font.BOLD, 30));
        box.addItem("EASY");
        box.addItem("NORMAL");
        box.addItem("HARD");
        box.setBackground(new Color(173,255,47));
        difficulty.add(box);
        difficulty.setBackground(new Color(173,255,47));

        menu = new MyPanel();
        menu.setBounds(0,0,800,600);
        menu.paintComponents(getGraphics());
        menu.setLayout(null);
        menu.add(Solo);
        menu.add(Double);
        menu.add(difficulty);

        this.setIconImage(image);
        this.add(menu);
        this.addKeyListener(kl);
        this.setTitle("TankWar");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Solo == e.getSource()) {
            tc = new TankClient();
            tc.j = false;

            if (box.getSelectedItem().toString().equals("EASY")) {

                Tank.diff = 38;
            }
            if (box.getSelectedItem().toString().equals("NORMAL")) {
                Tank.diff = 25;
            }
            if (box.getSelectedItem().toString().equals("HARD")) {
                Tank.diff = 15;
            }

            tc.lauchFrame();
        }

        if (Double == e.getSource()) {
            tc = new TankClient();
            tc.j = true;

            if (box.getSelectedItem().toString().equals("EASY")) {

                Tank.diff = 38;
            }
            if (box.getSelectedItem().toString().equals("NORMAL")) {
                Tank.diff = 25;
            }
            if (box.getSelectedItem().toString().equals("HARD")) {
                Tank.diff = 15;
            }
            tc.lauchFrame();
        }

    }


    KeyListener kl = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_X) {
                tc.dispose();
                System.out.println("ok");
            }

        }
    };

}


