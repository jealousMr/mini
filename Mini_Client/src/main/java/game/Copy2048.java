package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import javax.swing.JTextField;

public class Copy2048 extends JFrame{
    private JPanel scoresPane;
    private JPanel mainPane;
    private JLabel labelMaxScores ;
    private JLabel labelScores;
    private JLabel tips;
    private JTextField textMaxScores;
    private JLabel textScores;
    private JLabel[][] texts;
    private Icon icon2;
    private int times = 16;
    private int scores = 0;
    private int l1,l2,l3,l4,l5;
    Font font = new Font("", Font.BOLD,14);
    Font font2 = new Font("", Font.BOLD,30);
    Random random = new Random();

    public static void main(String[] args) {
        new Copy2048().ini2048();
    }

    public Copy2048(){
        super();
        setResizable(false);
        getContentPane().setLayout(null);
        setBounds(500, 50, 500, 615);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        setTitle("2048");

        scoresPane = new JPanel();
        scoresPane.setBackground(Color.green);
        scoresPane.setBounds(20, 20, 460, 40);
        scoresPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.YELLOW));
        getContentPane().add(scoresPane);
        scoresPane.setLayout(null);

        labelMaxScores = new JLabel("BEST:");
        labelMaxScores.setFont(font);
        labelMaxScores.setBounds(10, 5, 50, 30);
        scoresPane.add(labelMaxScores);

        textMaxScores = new JTextField("UNUSED");
        textMaxScores.setBounds(60, 5, 150, 30);
        textMaxScores.setFont(font);
        textMaxScores.setEditable(false);
        scoresPane.add(textMaxScores);

        labelScores = new JLabel("SCORES:");
        labelScores.setFont(font);
        labelScores.setBounds(240, 5, 70, 30);
        scoresPane.add(labelScores);

        textScores = new JLabel(String.valueOf(scores));
        textScores.setFont(font);
        textScores.setBounds(290, 5, 150, 30);
        scoresPane.add(textScores);

        mainPane = new JPanel();
        mainPane.setBounds(20, 70, 460, 500);

        this.getContentPane().add(mainPane);
        mainPane.setLayout(null);

        texts = new  JLabel[4][4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                texts[i][j] = new JLabel();
                texts[i][j].setFont(font2);
                texts[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                texts[i][j].setText("");
                texts[i][j].setBounds(120 * j, 120 * i, 100, 100);
                setColor(i, j, "");
                texts[i][j].setOpaque(true);
                texts[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.green));
                mainPane.add(texts[i][j]);

            }
        }

        tips = new JLabel("Tips:use UP,DOWN,LEFT,RIGHT OR W,S,A,D TO CONTROL");
        tips.setFont(font);
        tips.setBounds(60,480,400,20);
        mainPane.add(tips);

        textMaxScores.addKeyListener(new KeyAdapter(){
            public void keyPressed(  KeyEvent e){
                do_label_keyPressed(e);
            }
        });

        Create2();
        Create2();
    }

    public void ini2048() {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                    Copy2048 frame = new Copy2048();
                    frame.setVisible(true);
                    //    Thread thread = new Thread(frame);
                    //    thread.start();
                }
                catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }


    protected  void do_label_keyPressed(final KeyEvent e){
        int code = e.getKeyCode();
        int a ;
        String str ;
        String str1;
        int num;
        switch(code){
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                for(int i = 0; i < 4; i++){
                    a = 5;
                    for(int k = 0; k < 3; k++){
                        for(int j = 1; j < 4; j++){
                            str = texts[i][j].getText();
                            str1 = texts[i][j-1].getText();

                            if(str1.compareTo("") == 0){
                                texts[i][j-1].setText(str);
                                setColor(i, j-1,str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                            }else if((str.compareTo(str1) == 0) && (j !=a) && (j != a-1)){
                                num  = Integer.parseInt(str);
                                scores += num;
                                times ++;
                                str = String.valueOf(2 * num);
                                texts[i][j-1].setText(str);
                                setColor(i, j-1, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                                a = j;
                            }
                        }
                    }
                }
                l1 = 1;
                Create2();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                for(int i = 0; i < 4; i ++){
                    a = 5;
                    for(int k = 0; k < 3; k++){
                        for(int j = 2; j >= 0; j--){
                            str = texts[i][j].getText();
                            str1 = texts[i][j + 1].getText();

                            if(str1.compareTo("") == 0){
                                texts[i][j + 1].setText(str);
                                setColor(i, j+1, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                            }
                            else if(str.compareTo(str1) == 0 && j !=a && j != a+ 1){
                                num  = Integer.parseInt(str);
                                scores += num ;
                                times ++;
                                str = String.valueOf(2 * num);
                                texts[i][j + 1].setText(str);
                                setColor(i, j+1, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                                a = j;
                            }
                        }
                    }
                }
                l2 = 1;
                Create2();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                for(int j = 0; j < 4; j++){
                    a = 5;
                    for(int k = 0; k < 3; k++){
                        for(int i = 1; i < 4; i++){
                            str = texts[i][j].getText();
                            str1 = texts[i - 1][j].getText();

                            if(str1.compareTo("") == 0){
                                texts[i - 1][j].setText(str);
                                setColor(i-1, j, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                            }
                            else if(str.compareTo(str1) == 0 && i != a && i != a -1){
                                num  = Integer.parseInt(str);
                                scores += num ;
                                times ++;
                                str = String.valueOf(2 * num);
                                texts[i - 1][j].setText(str);
                                setColor(i-1, j, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                                a = i;
                            }
                        }
                    }
                }
                l3 =1;
                Create2();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                for(int j = 0; j < 4; j ++){
                    a = 5;
                    for(int k = 0; k < 5; k++){
                        for(int i = 2; i >= 0; i--){
                            str = texts[i][j].getText();
                            str1 = texts[i + 1][j].getText();

                            if(str1.compareTo("") == 0){
                                texts[i + 1][j].setText(str);
                                setColor(i+1, j, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                            }
                            else if(str.compareTo(str1) == 0 && i != a && i != a + 1){
                                num  = Integer.parseInt(str);
                                scores += num ;
                                times ++;
                                str = String.valueOf(2 * num);
                                texts[i + 1][j].setText(str );
                                setColor(i+1, j, str);
                                texts[i][j].setText("");
                                setColor(i, j, "");
                                a = i;
                            }
                        }
                    }
                }
                l4 = 1;
                Create2();
                break;
            default:
                break;
        }
        textScores.setText(String.valueOf(scores));
    }


    public void Create2(){
        int i ,j;
        boolean r = false;
        String str;

        if(times > 0){
            while(!r){
                i = random.nextInt(4);
                j = random.nextInt(4);
                str = texts[i][j].getText();
                if((str.compareTo("") == 0)){
                    texts[i][j].setIcon(icon2);
                    texts[i][j].setText("2");
                    setColor(i, j, "2");

                    times --;
                    r = true;
                    l1 = l2 = l3 = l4 = 0;
                }
            }
        }
        else if(l1 >0 && l2 >0 && l3 > 0 && l4 > 0){
            tips.setText("                            GAME¡¡OVER £¡");

        }
    }


    public void setColor(int i, int j, String str){
        switch(str){
            case "2":
                texts[i][j].setBackground(Color.yellow);
                break;
            case "4":
                texts[i][j].setBackground(Color.red);
                break;
            case "8":
                texts[i][j].setBackground(Color.pink);
                break;
            case "16":
                texts[i][j].setBackground(Color.orange);
                break;
            case "32":
                texts[i][j].setBackground(Color.magenta);
                break;
            case "64":
                texts[i][j].setBackground(Color.LIGHT_GRAY);
                break;
            case "128":
                texts[i][j].setBackground(Color.green);
                break;
            case "256":
                texts[i][j].setBackground(Color.gray);
                break;
            case "512":
                texts[i][j].setBackground(Color.DARK_GRAY);
                break;
            case "1024":
                texts[i][j].setBackground(Color.cyan);
                break;
            case "2048":
                texts[i][j].setBackground(Color.blue);
                break;
            case "":
            case "4096":
                texts[i][j].setBackground(Color.white);
                break;
            default:
                break;
        }

    }

}