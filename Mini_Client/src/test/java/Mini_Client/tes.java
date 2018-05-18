package Mini_Client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class tes {
    public static void main(String []args){
        Stage stage=new Stage();
        Button bu=new Button("ccc");
        ImageView im=new ImageView();
        VBox s=new VBox();
        s.getChildren().addAll(bu,im);
        Scene scene=new Scene(s,200,200);
        stage.setScene(scene);
        stage.show();

        bu.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            BufferedImage bf = null;
            try {
                bf = ImageIO.read(new File("C:/my/ppt/tu/g.jpg"));
            } catch (IOException ex) {
                System.out.println("Image failed to load.");
            }

            WritableImage wr = null;
            if (bf != null) {
                wr = new WritableImage(bf.getWidth(), bf.getHeight());
                PixelWriter pw = wr.getPixelWriter();
                for (int x = 0; x < bf.getWidth(); x++) {
                    for (int y = 0; y < bf.getHeight(); y++) {
                        pw.setArgb(x, y, bf.getRGB(x, y));
                    }
                }
            }
            im.setImage(wr);
        });
    }
    Socket s=null;
    public DataOutputStream dos=null;
    public DataInputStream dis=null;
    BufferedImage image;
    public ImageView im(BufferedImage ims){
        ImageView im=new ImageView();
        BufferedImage bf =ims;
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }
        im.setImage(wr);
        return  im;
    }
    public void connect(){
        try {
            s = new Socket("localhost", 10001);
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
            send("6\t");

            byte b[]=new byte[112048];
            dis.read(b);
            ByteArrayInputStream bin = new ByteArrayInputStream(b);
            BufferedImage image = ImageIO.read(bin);
            System.out.println("ok");

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public void send(String str){
        try {
            byte b[]=str.getBytes();
            dos.write(b);
            dos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void disconnect(){
        try {
            dis.close();
            dos.close();
            s.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
