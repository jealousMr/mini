package Serve;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ImgSend {
    private String IG;
    boolean flag=true;

    private ServerSocket serverSocket=null;
    public ImgSend(int port,String IG) throws IOException {
        serverSocket=new ServerSocket(port);
        this.IG=IG;
    }
    public void exe(){
        try {
            Socket socket = null;
                socket = serverSocket.accept();
                new ServerSocketThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class ServerSocketThread extends Thread {

        private Socket socket;

        public ServerSocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            try {
                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr); //
                String message = null;
                String j = "";
                while ((message = br.readLine()) != null) {
                    System.out.println("客户端发来消息：" + message);
                    j = j + message;
                }
                // 必须先关闭输入流才能获取下面的输出流
                socket.shutdownInput();
                JSONObject json = new JSONObject();
                json = json.parseObject(j);
                // 获取输出流
                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                if (json != null) {

                    String state = json.getString("state");
                    if(state.equals("img")){
                        pw.write(IG);
                    }

                }
                pw.flush();
                // 关闭输入流
                socket.shutdownOutput();
                flag=!flag;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (pw != null) {
                    pw.close();
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
