package Serve;

import Bean.newsImg;
import DBDao.newsImgDao;
import DBDaoImpl.newsImgDaoImpl;
import OtherUtil.Base64;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class newsImgServe {
    private ServerSocket serverSocket=null;
    public newsImgServe(int port) throws IOException {
        serverSocket=new ServerSocket(port);
    }
    public void exe(){
        try {
            System.out.println("******mainPage服务器已启动，等待客户端连接*****");
            Socket socket = null;
            while(true){
                //循环监听客户端的连接
                socket = serverSocket.accept();
                //新建一个线程ServerSocket，并开启
                new ServerSocketThread(socket).start();
            }
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
                String j="";
                while ((message = br.readLine()) != null) {
                    System.out.println("客户端发来消息：" + message);
                    j=j+message;
                }
                socket.shutdownInput();
                JSONObject json=new JSONObject();
                json=json.parseObject(j);
                // 获取输出流
                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                if(json!=null){
                    String id=json.getString("id");
                    int IDs=0;
                    try {
                         IDs=Integer.parseInt(id);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    String str=getImg("rep","png",IDs);
                    pw.write(str);
                }
                // pw.write("欢饮您进入socket");
                pw.flush();
                // 关闭输入流
                socket.shutdownOutput();
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
    private  String getImg(String name, String type, int id){  //newsImg对象封装为json
        String url="C:\\my\\projectText\\Mini_Serve\\src\\main\\resources\\imgSource\\"+name+"."+type;
        newsImgDao dao=new newsImgDaoImpl();
        dao.readImg(url,id);
        String img=Base64.changeToBase(url);
        String title=dao.getTitleById(id);
        JSONObject json=new JSONObject();
        json.put("img",img);
        json.put("title",title);
        return json.toJSONString();
    }

}
