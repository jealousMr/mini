package ConnectUtil;

import java.io.*;
import java.net.Socket;

public class FriendConn {
    Socket s=null;
    public DataOutputStream dos=null;
    public DataInputStream dis=null;

    public void connect() {

        try {
            s = new Socket("localhost", 10010);
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
