package DBDaoImpl;

import Bean.User;
import DBDao.registerDao;
import DBUtil.DB;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class registerDaoImpl implements registerDao {
    @Override
    public void add(User user) {
        String sql="insert into user(userNumber,password,nickname,email,online)values(?,?,?,?,?)";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,user.getUserNumber());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getNickName());
            pstmt.setString(4,user.getEmail());
            pstmt.setInt(5,user.getOnline());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public void delete(int id) {
        String sql="DELETE FROM user WHERE id = ?";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public User getUserByUserNumber(String UserNumber) {
        String sql="SELECT id,userNumber,password,nickname,email,online,gonline FROM user WHERE userNumber = ?";
        Connection conn=DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,UserNumber);//1对应第一个?;
            ResultSet rs=  pstmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String password=rs.getString(3);
                String nickname=rs.getString(4);
                String email=rs.getString(5);
               int online=rs.getInt(6);
                int gonline=rs.getInt(7);
               User us=new User();
               us.setEmail(email);
               us.setNickName(nickname);
               us.setPassword(password);
               us.setUserNumber(UserNumber);
               us.setOnline(online);
               us.setGonline(gonline);
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
    public void updateOnlineByUserNumber(String userNumber,boolean isOnline) {
        String sql="update user set online=? WHERE userNumber=?";
        int state=0;
        if(isOnline){
            state=1;//在线
        }else {
            state=-1;//不在线
        }
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,state);
            pstmt.setString(2,userNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public void updateoGnlineByUserNumber(String userNumber, boolean isOnline) {
        String sql="update user set gonline=? WHERE userNumber=?";
        int state=0;
        if(isOnline){
            state=1;//在线
        }else {
            state=-1;//不在线
        }
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,state);
            pstmt.setString(2,userNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public void updataNickname(String userNumber, String nickname) {
        String sql="update user set nickname=? WHERE userNumber=?";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,nickname);
            pstmt.setString(2,userNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

}
