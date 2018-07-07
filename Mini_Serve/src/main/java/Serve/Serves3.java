package Serve;

import Bean.User;
import Bean.UserMessage;
import DBDao.friendsDao;
import DBDao.registerDao;
import DBDao.userMessageDao;
import DBDaoImpl.friendsDaoImpl;
import DBDaoImpl.registerDaoImpl;
import DBDaoImpl.userMessageDaoImpl;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import javax.jws.soap.SOAPBinding;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Serves3 {
    public static void main(String[]args) throws IOException {
        Serves3 de=new Serves3();
        de.start();
    }
    ServerSocket ss=null;
    Socket s=null;
    List<Client> clients=new ArrayList<>();

    List<groupFile> groupFiles=new ArrayList<>();
    List<friends>friendsList=new ArrayList<>();
    List<chat>chats=new ArrayList<>();
    List<paint>paints=new ArrayList<>();

    //好友socket
    ServerSocket friendServerSocket=null;
    Socket friendSocket=null;

    //群文件socket
    ServerSocket fileServerSocket=null;
    Socket fileSocket=null;

    //好友单独聊天
    ServerSocket chatServerSocket=null;
    Socket chatSocket=null;

    //好友你画我猜
    ServerSocket paintServerSocket=null;
    Socket paintSocket=null;

    public void start() throws IOException {
        try {
            ss=new ServerSocket(10008);
            fileServerSocket=new ServerSocket(10009);
            friendServerSocket=new ServerSocket(10010);
            chatServerSocket=new ServerSocket(10011);
            paintServerSocket=new ServerSocket(10012);



            while (true){
                s=ss.accept();
                Client c=new Client(s);
                new Thread(c).start();
                clients.add(c);

                fileSocket=fileServerSocket.accept();
                groupFile g=new groupFile(fileSocket);
                new Thread(g).start();
                groupFiles.add(g);

                friendSocket=friendServerSocket.accept();
                friends f=new friends(friendSocket);
                new Thread(f).start();
                friendsList.add(f);

                chatSocket=chatServerSocket.accept();
                chat ca=new chat(chatSocket);
                new Thread(ca).start();
                chats.add(ca);

                paintSocket=paintServerSocket.accept();
                paint pai=new paint(paintSocket);
                new Thread(pai).start();
                paints.add(pai);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ss.close();
        }
    }
class paint implements Runnable{
    private Socket s;
    private DataInputStream dis=null;
    private DataOutputStream dos=null;
    private String own;
    private String friends;
    public paint(Socket s) throws IOException {
        this.s=s;
        dis=new DataInputStream(s.getInputStream());
        dos=new DataOutputStream(s.getOutputStream());
    }
    @Override
    public void run() {
        String fuserName="";
        try {
            own=dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                fuserName=dis.readUTF();
            }catch (SocketException s){
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }  while (true){
                try {
                    String close=dis.readUTF();
                   // System.out.println("挂壁"+close);
                    if (close.equals("2c")){
                        dos.writeUTF("2c");
                        break;
                    }
                    int x1=dis.readInt();
                    int y1=dis.readInt();
                    int x2=dis.readInt();
                    int y2=dis.readInt();
                    int rgb=dis.readInt();
                    int w=dis.readInt();
                    for (int i=0;i<paints.size();i++){
                        if (paints.get(i).own.equals(fuserName)){
                            paints.get(i).dos.writeUTF("tem");
                            paints.get(i).dos.writeInt(x1);
                            paints.get(i).dos.writeInt(y1);
                            paints.get(i).dos.writeInt(x2);
                            paints.get(i).dos.writeInt(y2);
                            paints.get(i).dos.writeInt(rgb);
                            paints.get(i).dos.writeInt(w);
                        }
                    }
                }catch (SocketException s){
                    break;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

public class chat implements  Runnable{
        private Socket s;
        private DataInputStream dis=null;
        private DataOutputStream dos=null;
        private String own;
        private String friends;

        public chat(Socket s) throws IOException {
            this.s=s;
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        }
        private void mainchat() throws IOException, InterruptedException {
            while (true){
                String tip=dis.readUTF();
                System.out.println(tip);
                if (tip.equals("img")){
                    String s=dis.readUTF();
                    System.out.println(s);
                    JSONObject js=new JSONObject().parseObject(s);
                    String imgLength=js.getString("imgLength");
                    String imgName=js.getString("imgName");
                    FileOutputStream fo=new FileOutputStream("C:\\my\\ppt\\tu\\serveMini\\"+imgName);
                    int len=0;
                    byte bytes[]=new byte[Integer.parseInt(imgLength)];
                    len=dis.read(bytes);
                    fo.write(bytes,0,len);
                    fo.close();
                    registerDao dao=new registerDaoImpl();
                    User user=dao.getUserByUserNumber(friends);
                    if (user.getOnline()==1){//在线
                        for (int i=0;i<chats.size();i++){
                            if (chats.get(i).own.equals(friends)){
                                File file=new File("C:\\my\\ppt\\tu\\serveMini\\"+imgName);
                                chats.get(i).dos.writeUTF("img");
                                chats.get(i).sendFile(file, (int) file.length());
                            }
                        }
                    }
                }else if (tip.equals("file")){
                    String fileName=dis.readUTF();
                    String le=dis.readUTF();
                    FileOutputStream fo=new FileOutputStream("C:\\my\\ppt\\tu\\serveMini\\"+fileName);
                    int len=0;
                    byte bytes[]=new byte[Integer.parseInt(le)];
                    len=dis.read(bytes);
                    fo.write(bytes,0,len);
                    fo.close();
                    Thread.sleep(1000);
                    for (int i=0;i<chats.size();i++){
                        if (chats.get(i).own.equals(friends)){
                            File file=new File("C:\\my\\ppt\\tu\\serveMini\\"+fileName);
                            chats.get(i).dos.writeUTF("file");
                           chats.get(i). dos.writeUTF(file.getName());
                            chats.get(i).dos.writeUTF(Long.toString(file.length()));
                            chats.get(i).sendFile(file, (int) file.length());
                        }
                    }


                }else if (tip.equals("close")){
                    System.out.println("关闭聊天串口界面");
                    dos.writeUTF("close");
                    break;
                }else {
                for (int i=0;i<chats.size();i++){
                    if (chats.get(i).own.equals(friends)){
                        chats.get(i).dos.writeUTF(tip);
                    }
                }
                }
            }
        }
        public void sendFile(File file, int length){
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
        @Override
        public void run() {
            try {
                own=dis.readUTF();

            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true){
                try {
                    friends=dis.readUTF();
                    System.out.println("socket chat"+own+"和"+friends+"交谈");
                    try {
                        mainchat();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (SocketException s){
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

class friends implements Runnable{
        private Socket s;
        private DataInputStream dis=null;
        private DataOutputStream dos=null;
        private String userNumber;
        public friends(Socket s)throws IOException{
            this.s=s;
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        }

        @Override
        public void run() {
            try {
                userNumber=dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("socket3  ="+userNumber);
            while (true) {
                try {
                    String ti = dis.readUTF();
                    if (ti.equals("00")){
                        System.out.println("退出程序3");
                        break;
                    }
                    System.out.println("请求好友列表：" + ti);
                    friendsDao fb = new friendsDaoImpl();//遍历好友
                    registerDao rd = new registerDaoImpl();//获取昵称,在线状态
                    userMessageDao ud = new userMessageDaoImpl();//获取用户头像
                    List<String> imgURL = new ArrayList<>();
                    ArrayList<String> friendU = fb.getFriends(userNumber);
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < friendU.size(); i++) {
                        UserMessage um = new UserMessage();
                        um.setUserNumber(friendU.get(i));
                        um.setNickname(rd.getUserByUserNumber(friendU.get(i)).getNickName());
                        um.setOnline(rd.getUserByUserNumber(friendU.get(i)).getOnline());
                        imgURL.add(ud.getUserMessageByUserNumber(friendU.get(i)).getPhoto());
                        String a = Integer.toString(i);
                        jsonObject.put(a, um.json());
                    }
                    dos.writeUTF(Integer.toString(friendU.size()));
                    dos.writeUTF(jsonObject.toJSONString());
                    dos.flush();
                    //发送头像
                    for (int i = 0; i < imgURL.size(); i++){
                        FileInputStream fis = null;
                    try {

                        fis = new FileInputStream(imgURL.get(i));
                        //获取输出流
                        byte[] buf = new byte[1024];
                        int len = 0;
                        //2.往输出流里面投放数据
                        while ((len = fis.read(buf)) != -1) {
                            dos.write(buf, 0, len);
                        }
                        System.out.println("头像发送万"+imgURL.get(i));
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        fis.close();
                    }
                    }

                } catch (SocketException s){
                    break;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

class groupFile implements Runnable{
    private Socket s;
    private DataInputStream dis=null;
    private DataOutputStream dos=null;
    private String userNumber;
    public groupFile(Socket s) throws IOException {
        this.s=s;
        dis=new DataInputStream(s.getInputStream());
        dos=new DataOutputStream(s.getOutputStream());
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
    @Override
    public void run() {
        try {
            userNumber=dis.readUTF();
            System.out.println("socket2"+userNumber);
            while (true){
                try {
                    String fileName=dis.readUTF();
                    String fileLength=dis.readUTF();
                    System.out.println(fileName+"  来了开始处理群文件");
                    FileOutputStream fo=new FileOutputStream("C:\\my\\ppt\\tu\\serveMini\\"+fileName);
                    int len=0;
                    byte bytes[]=new byte[Integer.parseInt(fileLength)];
                    len=dis.read(bytes);
                    fo.write(bytes,0,len);
                    Thread.sleep(1000);
//回发
                    for (int i=0;i<groupFiles.size();i++){
                        if (groupFiles.get(i).userNumber.equals(userNumber)){continue;}
                        groupFiles.get(i).dos.writeUTF(fileName);
                        groupFiles.get(i).dos.writeUTF(fileLength);
                        groupFiles.get(i).sendFile(new File("C:\\my\\ppt\\tu\\serveMini\\"+fileName));
                    }
                    System.out.println("_______---");
                }catch (SocketException s){
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

class Client implements Runnable{
        private Socket s;
        private DataInputStream dis=null;
        private boolean isOnline=false;
        private DataOutputStream dos=null;
        private OutputStream out=null;
        private InputStream in=null;
        private String tipU;//每个用户的usernumber
        public Client(Socket s) throws IOException {
            this.s=s;
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
            out=s.getOutputStream();
            in=s.getInputStream();
        }
        public void send(String str){
            try {
                dos.writeUTF(str);
                dos.flush();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        public void send2(String str){
            byte[] b=str.getBytes();
            try {
                dos.write(b);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void registers() throws IOException {
            System.out.println("注册业务");
            String str=dis.readUTF();
            JSONObject js=new JSONObject().parseObject(str);
            User user=new User();
            user.setUserNumber(js.getString("userNumber"));
            user.setPassword(js.getString("password"));
            user.setEmail(js.getString("email"));
            user.setNickName(js.getString("nickname"));
            registerDao dao=new registerDaoImpl();
            friendsDao friendsDao=new friendsDaoImpl();
            friendsDao.addUserNumber(user.getUserNumber());
            dao.add(user);
        }
        private void  backPersonalMessage() throws IOException {
            JSONObject jo=new JSONObject();
            userMessageDao ds=new userMessageDaoImpl();
            UserMessage userMessage=ds.getUserMessageByUserNumber(tipU);
            registerDao dr=new registerDaoImpl();
            try {
                jo.put("nickname",dr.getUserByUserNumber(tipU).getNickName());
                jo.put("birthday",userMessage.getBirthday());
                jo.put("sex",userMessage.getSex());
                jo.put("sign",userMessage.getSign());
                jo.put("userNumber",tipU);
                jo.put("phone",userMessage.getPhone());
                send(jo.toJSONString());//先发个人信息
                //在发头像
                String url=userMessage.getPhoto();
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(url);
                    //获取输出流

                    byte[] buf = new byte[1024];
                    int len = 0;
                    //2.往输出流里面投放数据
                    while ((len = fis.read(buf)) != -1)
                    {
                        dos.write(buf,0,len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    fis.close();
                }
            }catch (NullPointerException e){
                System.out.println("个人信息数据为空");
                dos.writeUTF("0");
                dos.flush();
            }
        }
        private void login() throws IOException {
            String st=dis.readUTF();
            JSONObject rep=new JSONObject().parseObject(st);
            String usernumber=rep.getString("userNumber");
            String password=rep.getString("password");
            registerDao rd=new registerDaoImpl();
            if (rd.getUserByUserNumber(usernumber).getPassword().equals(password)){
                System.out.println("登陆成功");
                tipU=usernumber;
                send("1");
                try {
                    //设置为在线
                    registerDao rds=new registerDaoImpl();
                    rds.updateOnlineByUserNumber(tipU,true);
                    successful();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                send("2");
            }
        }
        private void updataPerson() throws IOException {
            System.out.println("更新个人信息");
            UserMessage userMessage=new UserMessage();
            byte b[]=new byte[1024];
            int len=dis.read(b);
            String str=new String(b,0,len);
            String states=str.split("\t")[0];
            JSONObject js=new JSONObject().parseObject(states);
            userMessageDao daos=new userMessageDaoImpl();
            if (js!=null){
                try {
                    userMessage.setNickname(js.getString("nickname"));
                    userMessage.setBirthday(js.getString("birthday"));
                    userMessage.setPhone(js.getString("phone"));
                    userMessage.setAnswerOne(js.getString("answerOne"));
                    userMessage.setSex(js.getString("sex"));
                    userMessage.setSign(js.getString("sign"));
                    userMessage.setUserNumber(tipU);

                }catch (Exception e){e.printStackTrace();}
            }
            //更新头像
            Integer a=(int)(Math.random()*100);
            String name=a.toString();
            String imgUrl="C:\\my\\ppt\\tu\\serveMini\\"+name;
            byte ph[]=new byte[1112048];
            dis.read(ph);
            ByteArrayInputStream bin = new ByteArrayInputStream(ph);
            BufferedImage image = ImageIO.read(bin);
            File file = new File (imgUrl);
            String format = "png";
            ImageIO.write(image,format,file);
            userMessage.setPhoto(imgUrl);
                if ( daos.getUserMessageByUserNumber(userMessage.getUserNumber())==null){
                daos.add(userMessage,imgUrl);
                }else {
                    daos.updataMessage(userMessage);
                }
            if (userMessage.getNickname()!=null){
                registerDao ds=new registerDaoImpl();
                ds.updataNickname(userMessage.getUserNumber(),userMessage.getNickname());
            }
        }
        private void getPerson() throws IOException {
            userMessageDao dao=new userMessageDaoImpl();
            UserMessage u=dao.getUserMessageByUserNumber(tipU);
            String nn=u.getNickname();
            JSONObject js=new JSONObject();
            js.put("userNumber",tipU);
            js.put("nickname",nn);
            send2(js.toJSONString());
            //发送头像
//            String url=u.getPhoto();
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(url);
//                //获取输出流
//
//                byte[] buf = new byte[1024];
//                int len = 0;
//                //2.往输出流里面投放数据
//                while ((len = fis.read(buf)) != -1)
//                {
//                    dos.write(buf,0,len);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                fis.close();
//            }
        }
        private void functionPage() throws IOException {
                        byte b2[]=new byte[1024];  //此处判断进入那个界面（朋友列表，功能，群聊）
                        int lens=dis.read(b2);
                        String s=new String(b2,0,lens);
                        String tip=s.split("\t")[0];
                        System.out.println(tip);
                        if (tip.equals("fun1")){//添加好友功能,03,f,fun1
                            int tem=0;
                            while (true){
                                byte b[]=new byte[1024];
                                int len=dis.read(b);
                                String s1=new String(b,0,len);
                                String str=s1.split("\t")[0];
                                if (str.equals("close")){
                                    tem=1;
                                    System.out.println("添加好友串口close2");
                                    send2("clo");
                                    break;
                                }if (str.equals("close2")){
                                    System.out.println("添加好友串口close");
                                    send2("clo");
                                    break;//用户关闭窗口
                                }
                                if (str.equals("fun1"))continue;
                                System.out.println(str);
                                userMessageDao rd=new userMessageDaoImpl();
                                JSONObject J=new JSONObject().parseObject(str);
                                UserMessage fuser=null;
                                try {
                                    fuser=rd.getUserMessageByUserNumber(J.getString("friend"));
                                    if (fuser==null){
                                        J.put("tip","noF");
                                        //不存在此人
                                        send2(J.toJSONString());
                                    }else {
                                        J.put("tip","yesF");
                                        J.put("userNumber",fuser.getUserNumber());
                                        send2(J.toJSONString());
                                    }
                                }catch (Exception et){
                                    J.put("tip","noF");
                                    //不存在此人
                                    send2(J.toJSONString());
                                }
                            }//     添加好友
                            if (tem==1){
                                byte b[]=new byte[1024];
                                int len=dis.read(b);
                                String s1=new String(b,0,len);
                                String str=s1.split("\t")[0];
                                JSONObject js=new JSONObject().parseObject(str);
                                String friends=js.getString("friend");
                                friendsDao fd=new friendsDaoImpl();
                                for (int p=1;p<=10;p++){
                                    if (fd.isKon(tipU,p)==false&&!fd.isFriend(tipU,friends)){
                                        fd.addFriends(tipU,friends,p);
                                        //实现互加
                                        if (fd.isFriend(friends,tipU)==false){
                                            for (int i=1;i<=10;i++){
                                                if (fd.isKon(friends,i)==false){
                                                    fd.addFriends(friends,tipU,i);
                                                    System.out.println(tipU+"添加好友成功"+friends);
                                                    break;
                                                }

                                            }
                                        }
                                       // System.out.println(tipU+"添加好友成功"+friends);
                                        break;
                                    }
                                }

                            }
                        }else if (tip.equals("addGc")){
                            System.out.println("关闭功能页面，加入群聊");
                            successful();
                        }

        }
        private void groupChat() throws IOException {
            System.out.println("加入群聊");
            registerDao registerDao3=new registerDaoImpl();
            registerDao3.updateoGnlineByUserNumber(tipU,true);
            while (true){
                byte bytes[]=new byte[1024];
                int les=dis.read(bytes);
                String repuest=new String(bytes,0,les);
                if (repuest.equals("10004")){
                    System.out.println("退出群聊了");
                    registerDao3.updateoGnlineByUserNumber(tipU,false);
                    dos.write("{\"tip\":\"10004\"}".getBytes());
                    dos.flush();
                    break;
                }
                    JSONObject js=new JSONObject().parseObject(repuest);
                    if (js!=null){
                        for (int i=0;i<clients.size();i++){
                            if(clients.get(i).tipU.equals(this.tipU)){}//排除自己
                            else {
                                js.put("userNumber",this.tipU);
                                registerDao rd=new registerDaoImpl();
                                int on=rd.getUserByUserNumber(clients.get(i).tipU).getGonline();
                                if(on==1)//只发送给在线用户
                                    clients.get(i).send2(js.toJSONString());
                            }
                        }
                    }

            }

        }
        private void successful() throws IOException {
            while (true){
                try {
                    byte b[]=new byte[1024];
                    int len=dis.read(b);
                    String str=new String(b,0,len);
                    System.out.println(str);
                    String states=str.split("\t")[0];

                    if (states.equals("01")){//读取个人信息
                        backPersonalMessage();
                    }else if (states.equals("02")){//更新个人信息
                        updataPerson();
                    }else if (states.equals("03")){//聊天界面
                        System.out.println("进入好友列表界面");
                        byte b2[]=new byte[1024];  //此处判断进入那个界面（朋友列表，功能，群聊）
                        int lens=dis.read(b2);
                        String s=new String(b2,0,lens);
                        String tip=s.split("\t")[0];
                        if (tip.equals("f")){//03,f
                            System.out.println("进入功能界面");
                            functionPage();
                        } else if (tip.equals("p")){//03,p
                            getPerson();//获得个人信息
                        }else if (tip.equals("g")){//03,g
                            groupChat();
                        }
                    }
                }catch (SocketException s){
                    registerDao d=new registerDaoImpl();
                    d.updateOnlineByUserNumber(tipU,false);
                    //释放容器
                    for (int i=0;i<clients.size();i++){
                        if (clients.get(i).tipU.equals(tipU)){
                            clients.remove(i);
                        }
                    }
                    System.out.println("退出程序");
                    break;
                }

            }
        }
        @Override
        public void run() {

            try {

                String states=dis.readUTF();
                System.out.println(states);
                    int state=Integer.parseInt(states);
                    if (state==100){//注册业务
                        registers();
                    }else if (state==101){//用户登陆后的各种信息
                        login();
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
