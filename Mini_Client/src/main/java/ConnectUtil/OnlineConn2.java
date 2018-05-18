package ConnectUtil;

import java.io.*;
import java.net.Socket;

public class OnlineConn2 {
    Socket s=null;
    public DataOutputStream dos=null;
    public DataInputStream dis=null;
    public OutputStream out=null;
    public InputStream in=null;
    public FileConn fileConn=new FileConn();
    public FriendConn friendConn=new FriendConn();
    public ChatConn chatConn=new ChatConn();
    public PaintConn paintConn=new PaintConn();
    public void connect(){
        try {
            s = new Socket("localhost", 10008);
            dos=new DataOutputStream(s.getOutputStream());
            dis=new DataInputStream(s.getInputStream());
            out=s.getOutputStream();
            in=s.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendFile(File file,int length){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream=new FileInputStream(file);
            byte[] b=new byte[length];

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
    public void sends(String str){
        byte[] b=str.getBytes();
        try {
            dos.write(b);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(String str){
        try {
            dos.writeUTF(str);
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
            out.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
