import DBDao.friendsDao;
import DBDao.registerDao;
import DBDao.userMessageDao;
import DBDaoImpl.friendsDaoImpl;
import DBDaoImpl.registerDaoImpl;
import DBDaoImpl.userMessageDaoImpl;
import OtherUtil.Base64;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class teeee {

    public static void main(String[]args){
       userMessageDao d=new userMessageDaoImpl();
       System.out.println(d.getUserMessageByUserNumber("3122"));
    }

}
