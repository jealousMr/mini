package ConnectUtil;

import java.io.*;
import java.net.Socket;

public class Conn {
    Socket socket = null;
    OutputStream os = null;
    PrintWriter pw = null;
    InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    private int port;
    private String host;
    public Conn(int port,String host) throws IOException {
        this.host=host;
        this.port=port;
        socket=new Socket(host,port);
        os=socket.getOutputStream();
        pw=new PrintWriter(os);
    }
    public void sends(String str) throws IOException {
        pw.write(str);
        pw.flush();
        socket.shutdownOutput();
    }
    public String gets() throws IOException {
        is=socket.getInputStream();
        isr=new InputStreamReader(is);
        br=new BufferedReader(isr);
        String str="";
        String re="";
        while((str=br.readLine())!=null){
            re=re+str;
            //System.out.println(str);
        }
        socket.shutdownInput();
        return re;
    }
    public void close() throws IOException {
        br.close();
        isr.close();
        is.close();
        os.close();socket.close();
    }
}
