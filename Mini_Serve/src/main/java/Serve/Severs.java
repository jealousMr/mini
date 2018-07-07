package Serve;

import Bean.User;
import Bean.UserMessage;
import DBDao.friendsDao;
import DBDao.registerDao;
import DBDao.userMessageDao;
import DBDaoImpl.friendsDaoImpl;
import DBDaoImpl.registerDaoImpl;
import DBDaoImpl.userMessageDaoImpl;
import OtherUtil.Base64;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Severs {
    boolean stareted=false;
    ServerSocket ss=null;
    Socket s=null;
    List<Serves3.Client> clients=null;
    public Severs(List<Serves3.Client> clients){
        this.clients=clients;
    }

    public void start() throws IOException {
        try {
            ss=new ServerSocket(10000);
            stareted=true;
            while (stareted) {
                s = ss.accept();
                Client c = new Client(s);
                new Thread(c).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ss.close();
        }
    }
    class Client implements Runnable{
        private Socket s;
        private DataInputStream dis=null;
        private DataOutputStream dos=null;
        public Client(Socket s) throws IOException {
            this.s=s;
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        }
        public void send(String str){

        }
        @Override
        public void run() {


        }

    }
}
