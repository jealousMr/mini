package chatPage;

import ConnectUtil.OnlineConn2;
import javafx.concurrent.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class paneSwing extends JFrame implements ActionListener, MouseMotionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    public JButton line;
    public JButton color;
    public Color Col, Col1;
    public JButton clear;
    public JButton clears;
    public JButton width;
    public JPanel canva;
    public int x1, y1;
    public Graphics2D g;
    public BasicStroke strock;
    public int w, w1;
    public JComboBox<Integer> box;
    private OnlineConn2 onlineConn2;
    private String friends;
    private String close="1c";
    public paneSwing(OnlineConn2 onlineConn2,String friends){
        this.onlineConn2=onlineConn2;
        this.friends=friends;
    }

    public void iniFrame() throws IOException {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("关闭你画我猜");
                close="2c";
                try {
                    onlineConn2.paintConn.dos.writeUTF(close);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        canva = new JPanel();
        canva.setBounds(95, 1, 515, 550);
        canva.setBackground(Color.white);
        canva.addMouseListener(this);
        canva.addMouseMotionListener(this);

        line = new JButton("画线");
        line.setBounds(1, 1, 90, 70);
        line.addActionListener(this);

        color = new JButton("颜色");
        color.setBounds(1, 70, 90, 70);
        color.addActionListener(this);

        clear = new JButton("橡皮");
        clear.setBounds(1, 140, 90, 70);
        clear.addActionListener(this);

        clears = new JButton("清屏");
        clears.setBounds(1, 210, 90, 70);
        clears.addActionListener(this);

        width = new JButton("粗细");
        width.setBounds(1, 280, 90, 70);
        box = new JComboBox<Integer>();
        box.setBounds(380, 15, 80, 30);
        for (int i = 0; i < 10; i++) {
            Integer intdata = new Integer(i + 1);
            box.addItem(intdata);
        }
        box.addItem(1000);
        width.add(box);
        width.addActionListener(this);

    //    control = new controller(socket, this);
        this.add(canva);
        this.add(line);
        this.add(color);
        this.add(clear);
        this.add(clears);
        this.add(width);
        this.setBackground(new Color(199,212,247));
        this.setTitle("画板");
        this.setLayout(null);
        this.setSize(600, 600);
//        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        g = (Graphics2D) canva.getGraphics();
     //   control.dealwith();

        MyThread thread=new MyThread();
        thread.start();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (color == e.getSource()) {

            new JColorChooser();
            Col = JColorChooser.showDialog(this, "选择颜色", Color.BLACK);
        } else if (clear == e.getSource()) {
            Col = Color.WHITE;
        } else if (line == e.getSource()) {
            if (Col == null) {
                Col = Color.black;
            }
            w = (int) box.getSelectedItem();
        } else if (clears == e.getSource()) {
            Col1 = Col;
            Col = Color.WHITE;
            w1 = w;
            w = 10000;
        } else if (width == e.getSource()) {

        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        strock = new BasicStroke(w);
        g.setStroke(strock);

        g.setColor(Col);
        int x2 = e.getX();

        int y2 = e.getY();

        g.drawLine(x1, y1, x2, y2);
        try {

          onlineConn2.paintConn.dos.writeUTF(close);
            onlineConn2.paintConn.dos.writeInt(x1);
            onlineConn2.paintConn.dos.writeInt(y1);
            onlineConn2.paintConn.dos.writeInt(x2);
            onlineConn2.paintConn.dos.writeInt(y2);
            onlineConn2.paintConn.dos.writeInt(g.getColor().getRGB());
            onlineConn2.paintConn.dos.writeInt(w);
            x1 = x2;
            y1 = y2;
        } catch (Exception e1) {
        }

    }

    @Override
    public void mouseMoved(MouseEvent arg0) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (w == 10000) {
            w = w1;
            Col = Col1;
        }

    }

    class MyThread extends Thread {

        @Override
        public void run() {
            while (true) {
                int x1 = 0;
                try {
                    String close=onlineConn2.paintConn.dis.readUTF();
                    if (close.equals("2c")){
                        break;
                    }
                    x1 = onlineConn2.paintConn.dis.readInt();
                    int y1 = onlineConn2.paintConn.dis.readInt();
                    int x2 = onlineConn2.paintConn.dis.readInt();
                    int y2 = onlineConn2.paintConn.dis.readInt();
                    int rgb = onlineConn2.paintConn.dis.readInt();
                    int w = onlineConn2.paintConn.dis.readInt();
                    BasicStroke basicStroke = new BasicStroke(w);
                    g.setColor(new Color(rgb));
                    g.setStroke(basicStroke);
                    g.drawLine(x1, y1, x2, y2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
