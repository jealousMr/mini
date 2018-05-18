package ConnectUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class PaintConn {
    Socket s=null;
    public DataOutputStream dos=null;
    public DataInputStream dis=null;
    public void connect() {
        try {
            s = new Socket("localhost", 10012);
            dos=new DataOutputStream(s.getOutputStream());
            dis=new DataInputStream(s.getInputStream());
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
