package ConnectUtil;

import com.alibaba.fastjson.JSONObject;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mainPage.MainPageView;


import java.io.*;
import java.net.Socket;

public class FileConn {
    Socket s=null;
   public DataOutputStream dos=null;
    public DataInputStream dis=null;

    public void connect() {

        try {
            s = new Socket("localhost", 10009);
            dos=new DataOutputStream(s.getOutputStream());
            dis=new DataInputStream(s.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sends(String str){
        byte[] b=str.getBytes();
        try {
            dos.write(b);
            dos.flush();
        } catch (IOException e) {
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
    public void sendFile(File file){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream=new FileInputStream(file);
            byte[] b=new byte[1024];

            while (fileInputStream.available()!=0){
                int len=fileInputStream.read(b);
                dos.write(b,0,len);
                dos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
