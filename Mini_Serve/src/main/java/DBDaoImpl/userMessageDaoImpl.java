package DBDaoImpl;

import Bean.User;
import Bean.UserMessage;
import DBDao.registerDao;
import DBDao.userMessageDao;
import DBUtil.DB;
import OtherUtil.Base64;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

public class userMessageDaoImpl implements userMessageDao {
    @Override
    public void add(UserMessage UserMessage,String ur) {
        String sql="insert into userMessage(userNumber,birthday,sex,sign,photo,answerOne,phone)values(?,?,?,?,?,?,?)";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,UserMessage.getUserNumber());
            pstmt.setString(2,UserMessage.getBirthday());
            pstmt.setString(3,UserMessage.getSex());
            pstmt.setString(4,UserMessage.getSign());
            Base64.changeToImg(UserMessage.getPhoto(),UserMessage.getUserNumber()+".png");
           // String url="C:\\my\\projectText\\Mini_Serve\\src\\main\\resources\\imgSource\\"+UserMessage.getUserNumber()+".png";
            pstmt.setString(5,ur);
            pstmt.setString(6,UserMessage.getAnswerOne());
            pstmt.setString(7,UserMessage.getPhone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(conn);
        }

}

    @Override
    public UserMessage getUserMessageByUserNumber(String UserNumber) {
        String sql="SELECT id,userNumber,birthday,sex,sign,photo,answerOne,phone FROM userMessage WHERE userNumber = ?";
        Connection conn=DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,UserNumber);//1对应第一个?;
            ResultSet rs=  pstmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String userNumber=rs.getString(2);
                String birthday=rs.getString(3);
                String sex=rs.getString(4);
                String sign=rs.getString(5);
                String photo=rs.getString(6);
                String ans1=rs.getString(7);
                String ans2=rs.getString(8);
                String nickname=null;
                registerDao dao=new registerDaoImpl();
                try {
                    User user = dao.getUserByUserNumber(userNumber);
                    nickname=user.getNickName();
                }catch (Exception e){
                    e.printStackTrace();
                }
              UserMessage us=new UserMessage();
              us.setSex(sex);
              us.setSign(sign);
              us.setPhoto(photo);
              us.setAnswerOne(ans1);
              us.setPhone(ans2);
              us.setBirthday(birthday);
              us.setUserNumber(userNumber);
              us.setNickname(nickname);

                return us;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
        return null;
    }

    @Override
    public void updataMessage(UserMessage userMessage) {
        String sql = "update userMessage set birthday=?,sex=?,sign=?,answerOne=?,phone=?,photo=?WHERE userNumber=?";
        Connection conn = DB.open();
        try {
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, userMessage.getBirthday());
            pstmt.setString(2, userMessage.getSex());
            pstmt.setString(3, userMessage.getSign());
            pstmt.setString(4, userMessage.getAnswerOne());
            pstmt.setString(5,userMessage.getPhone());
            pstmt.setString(6,userMessage.getPhoto());
            pstmt.setString(7,userMessage.getUserNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(conn);
        }

    }

    @Override
    public void updataPhoto(String url,String userNumber) {
        String sql = "update userMessage set photo=? WHERE userNumber=?";
        Connection conn = DB.open();
        try {
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

            pstmt.setString(1, url);
            pstmt.setString(2,userNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(conn);
        }

    }
}

