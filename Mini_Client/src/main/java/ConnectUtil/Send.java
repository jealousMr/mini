package ConnectUtil;

import java.io.*;
import java.net.Socket;

public class Send {
    private String host;
    private int port;
    Socket socket;
    public Send(String host,int port) throws IOException {
        this.host=host;
        this.port=port;
        socket=new Socket(host,port);
    }
    public void sends(String str) throws IOException {
        OutputStream os=socket.getOutputStream();
        PrintWriter pw=new PrintWriter(os);
        pw.write(str);
        pw.flush();
        pw.close();
        socket.close();
    }


    public String gets() throws IOException {
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String info=null;
        String s="";

        while((info=br.readLine())!=null){
            System.out.println("服务器："+info);
            s+=info;
        }
        br.close();
        is.close();
        socket.close();
        return s;
    }
}
