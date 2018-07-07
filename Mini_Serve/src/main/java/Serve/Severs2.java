package Serve;

import Bean.UserMessage;
import DBDao.friendsDao;
import DBDao.registerDao;
import DBDao.userMessageDao;
import DBDaoImpl.friendsDaoImpl;
import DBDaoImpl.registerDaoImpl;
import DBDaoImpl.userMessageDaoImpl;
import OtherUtil.Base64;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Severs2 {
    boolean stareted=false;
    ServerSocket ss=null;
    Socket s=null;
    List<Client> clients=new ArrayList<>();
    public static void main(String[]args) throws IOException {
        new Severs2().start();
    }
    public void start() throws IOException {
        try {
            ss=new ServerSocket(10007);
            stareted=true;
            while (stareted){
                s=ss.accept();
                Client c=new Client(s);
                new Thread(c).start();
                clients.add(c);
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
        private boolean isOnline=false;
        private DataOutputStream dos=null;
        private String tipU;//每个用户的usernumber

        public Client(Socket s) throws IOException {
            this.s=s;
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        }
        public void send(String str){
            try {
                dos.writeUTF(str);
                dos.flush();
            } catch (IOException e) {
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
        private void backPersonalMessage(String userNumber){
            JSONObject jo=new JSONObject();
            userMessageDao ds=new userMessageDaoImpl();
            UserMessage userMessage=ds.getUserMessageByUserNumber(userNumber);
            registerDao dr=new registerDaoImpl();
            jo.put("nickname",dr.getUserByUserNumber(userNumber).getNickName());
            jo.put("birthday",userMessage.getBirthday());
            jo.put("sex",userMessage.getSex());
            jo.put("sign",userMessage.getSign());
            jo.put("userNumber",userNumber);
            jo.put("phone",userMessage.getPhone());
            try {
                jo.put("photo",Base64.changeToBase(userMessage.getPhoto()));
            }catch (Exception e){
                e.printStackTrace();
            }
            send(jo.toJSONString());
        }
        private void updataPersonalMessage(JSONObject js){
            //System.out.println(js);
            String photo;
            userMessageDao daos=new userMessageDaoImpl();
            UserMessage userMessage=new UserMessage();
            userMessage.setUserNumber(js.getString("userNumber"));
            try {
                photo=js.getString("photo");
                Integer a=(int)(Math.random()*100);
                String name=a.toString();
                Base64.changeToImg(photo,name);
                String ul="C:\\my\\ppt\\tu\\serveMini\\"+name+".png";
                daos.updataPhoto(ul,userMessage.getUserNumber());
                System.out.println(userMessage.getUserNumber()+"更新"+ul);
            }catch (Exception e){e.printStackTrace();}
            try {
                userMessage.setNickname(js.getString("nickname"));
                userMessage.setBirthday(js.getString("birthday"));
                userMessage.setPhone(js.getString("phone"));
                userMessage.setAnswerOne(js.getString("answerOne"));
                userMessage.setSex(js.getString("sex"));
                userMessage.setSign(js.getString("sign"));
                userMessage.setUserNumber(js.getString("userNumber"));
            }catch (Exception e){e.printStackTrace();}

            if(userMessage.getBirthday()!=null)
                daos.updataMessage(userMessage);
            if (userMessage.getNickname()!=null){
                registerDao ds=new registerDaoImpl();
                ds.updataNickname(userMessage.getUserNumber(),userMessage.getNickname());
            }

        }
        private void groupChat(JSONObject js,String userNumber){
            for (int i=0;i<clients.size();i++){
                if(clients.get(i).tipU.equals(userNumber)){}//排除自己
                else {
                    js.put("userNumber",userNumber);
                    registerDao rd=new registerDaoImpl();
                    int on=rd.getUserByUserNumber(clients.get(i).tipU).getGonline();
                    if(on==1)//只发送给在线用户
                        clients.get(i).send(js.toJSONString());
                }
            }
        }
        private void findFriends(String fU){
            //查找是否存在此人
            userMessageDao rd=new userMessageDaoImpl();
            JSONObject J=new JSONObject();
            UserMessage fuser=null;
            try {
                fuser=rd.getUserMessageByUserNumber(fU);
                if (fuser==null){
                    J.put("tip","noF");
                    //不存在此人
                    send(J.toJSONString());
                }else {
                    J.put("tip","yesF");
                    J.put("userNumber",fuser.getUserNumber());
                    send(J.toJSONString());
                }
            }catch (Exception et){
                J.put("tip","noF");
                //不存在此人
                send(J.toJSONString());
            }
        }
        private void addFriends(JSONObject js){
            String friends=js.getString("friend");
            friendsDao fd=new friendsDaoImpl();
            for (int p=1;p<=10;p++){
                if (fd.isKon(tipU,p)==false&&!fd.isFriend(tipU,friends)){
                    fd.addFriends(tipU,friends,p);
                    System.out.println("添加好友成功");
                    break;
                }
            }
            send("{\"tip\":\"10007\"}");
        }
        private void backPC(){
            userMessageDao dao=new userMessageDaoImpl();
            UserMessage u=dao.getUserMessageByUserNumber(tipU);
            String nn=u.getNickname();
          //  String p=Base64.changeToBase(u.getPhoto());
            JSONObject js=new JSONObject();
            js.put("userNumber",tipU);
           // js.put("photo",p);
            js.put("nickname",nn);
            send(js.toJSONString());
        }
        private void groupFile(){

        }

        private void getFriendsList(){
            friendsDao fb=new friendsDaoImpl();//遍历好友
            registerDao rd=new registerDaoImpl();//获取昵称,在线状态
            userMessageDao ud=new userMessageDaoImpl();//获取用户头像
            String un=tipU;
            ArrayList<String>friendU=fb.getFriends(un);
            ArrayList<String>friendNick=new ArrayList<>();
            ArrayList<String>friendPhoto=new ArrayList<>();
            ArrayList<Integer>fonline=new ArrayList<>();

            for(int t=0;t<friendU.size();t++){
                fonline.add(rd.getUserByUserNumber(friendU.get(t)).getOnline());
                friendNick.add(rd.getUserByUserNumber(friendU.get(t)).getNickName());
                friendPhoto.add(Base64.changeToBase(ud.getUserMessageByUserNumber(friendU.get(t)).getPhoto()));
            }
            for (int y=0;y<friendU.size();y++){
                JSONObject jsonObject2=new JSONObject();
                jsonObject2.put("tip","FF");
                jsonObject2.put("online",fonline.get(y));
                jsonObject2.put("userNumber",friendU.get(y));
                jsonObject2.put("nickName",friendNick.get(y));
                jsonObject2.put("photo",friendPhoto.get(y));
                send(jsonObject2.toJSONString());
            }
            send("{\"tip\":\"-1\"}");
        }
        @Override
        public void run() {
            try {
                    //开始业务
                boolean Online=false;

                  while (true){
                      //登陆逻辑
                      String repuest=dis.readUTF();
                      JSONObject rep=new JSONObject().parseObject(repuest);//根据指令进行相应工作
                      if (rep!=null){
                          String rs=rep.getString("state");
                          if (rs.equals("10000")){
                              String usernumber=rep.getString("userNumber");
                              String password=rep.getString("password");
                              registerDao rd=new registerDaoImpl();
                              if (rd.getUserByUserNumber(usernumber).getPassword().equals(password)){
                                  System.out.println("登陆成功");
                                  Online=true;
                                  tipU=usernumber;
                                  send("{\"message\":\"1\"}");
                                  break;
                              }else {
                                  send("{\"message\":\"2\"}");
                              }
                          }
                      }
                    }
                    //登陆成功后
                    if (Online){
                      while (true){
                          String st=dis.readUTF();
                         System.out.println(st);
                          JSONObject rep=new JSONObject().parseObject(st);
                          if (rep!=null){
                              String state=rep.getString("state");
                              if (state.equals("10001")){//处理返回个人信息
                                  backPersonalMessage(tipU);
                              }else if(state.equals("10002")){//更新个人信息
                                  updataPersonalMessage(rep);
                                  System.out.println("个人信息更新成功");
                              }else if(state.equals("10003")){//加入群聊
                                  registerDao rd=new registerDaoImpl();
                                  rd.updateoGnlineByUserNumber(tipU,true);
                                groupChat(rep,tipU);
                              }else if(state.equals("10004")){//退出群聊
                                  registerDao rd=new registerDaoImpl();
                                  rd.updateoGnlineByUserNumber(tipU,false);
                                  send("{\"tip\":\"10004\"}");
                              }else if(state.equals("10005")){//咨询用户
                                  String fU=rep.getString("friend");
                                  findFriends(fU);
                              }else if (state.equals("10006")){//获得聊天个人信
                                  backPC();
                              }else if(state.equals("10007")){//添加好友
                                  addFriends(rep);
                              }else if (state.equals("10008")){//获取好友列表信息
                                    getFriendsList();
                              }else if (state.equals("10009")){//点击关闭好友添加页面
                                  send("{\"tip\":\"10009\"}");
                              }else if (state.equals("10010")){//群聊中发送文件
                                  groupFile();
                              }
                          }
                      }
                    }



            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(dis!=null)dis.close();
                    if(dos!=null)dos.close();
                    if(s!=null)s.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
