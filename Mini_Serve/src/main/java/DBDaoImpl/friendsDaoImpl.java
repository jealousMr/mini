package DBDaoImpl;

import DBDao.friendsDao;
import DBUtil.DB;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class friendsDaoImpl implements friendsDao {
    @Override
    public void addUserNumber(String userNumber) {
        String sql="insert into friends(userNumber)values(?)";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,userNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public void addFriends(String user,String friends, int pos) {
        String po=Integer.toString(pos);
        String friend="friends"+po;
        String sql="update friends set "+friend+"=? WHERE userNumber=?";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,friends);
            pstmt.setString(2,user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public boolean isKon(String user, int pos) {
        String sql="SELECT id,userNumber,friends1,friends2,friends3,friends4," +
                "friends5,friends6,friends7,friends8,friends9,friends10 FROM friends WHERE userNumber = ?";
        Connection conn=DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,user);//1对应第一个?;
            ResultSet rs=  pstmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String f1=rs.getString("friends1");
                String f2=rs.getString("friends2");
                String f3=rs.getString("friends3");
                String f4=rs.getString("friends4");
                String f5=rs.getString("friends5");
                String f6=rs.getString("friends6");
                String f7=rs.getString("friends7");
                String f8=rs.getString("friends8");
                String f9=rs.getString("friends9");
                String f10=rs.getString("friends10");
            String po=Integer.toString(pos);
            String temp="friends"+po;
            String la=rs.getString(temp);
            if (la.length()>2)
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
        return false;
    }

    @Override
    public ArrayList<String> getFriends(String userNumber) {
        String sql="SELECT id,userNumber,friends1,friends2,friends3,friends4,friends5,friends6,friends7,friends8,friends9,friends10 FROM friends WHERE userNumber = ?";
        Connection conn=DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,userNumber);//1对应第一个?;
            ResultSet rs=  pstmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String a1="",a2="",a3="",a4="",a5="",a6="",a7="",a8="",a9="",a10="";
                a1=rs.getString(3);
               a2=rs.getString(4);
                a3=rs.getString(5);
                a4=rs.getString(6);
                a5=rs.getString(7);
                a6=rs.getString(8);
                a7=rs.getString(9);
                a8=rs.getString(10);
                a9=rs.getString(11);
                a10=rs.getString(12);
                ArrayList<String> temp=new ArrayList<>();
                if (a1.length()>2)
                    temp.add(a1);
                if (a2.length()>2)
                    temp.add(a2);
                if (a3.length()>2)
                    temp.add(a3);
                if (a4.length()>2)
                    temp.add(a4);
                if (a5.length()>2)
                    temp.add(a5);
                if (a6.length()>2)
                    temp.add(a6);
                if (a7.length()>2)
                    temp.add(a7);
                if (a8.length()>2)
                    temp.add(a8);
                if (a9.length()>2)
                    temp.add(a9);
                if (a10.length()>2)
                    temp.add(a10);
                return temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
        return null;
    }

    @Override
    public boolean isFriend(String userNumber,String fr) {
        String sql="SELECT id,userNumber,friends1,friends2,friends3,friends4," +
                "friends5,friends6,friends7,friends8,friends9,friends10 FROM friends WHERE userNumber = ?";
        Connection conn=DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,userNumber);//1对应第一个?;
            ResultSet rs=  pstmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String f1=rs.getString("friends1");
                String f2=rs.getString("friends2");
                String f3=rs.getString("friends3");
                String f4=rs.getString("friends4");
                String f5=rs.getString("friends5");
                String f6=rs.getString("friends6");
                String f7=rs.getString("friends7");
                String f8=rs.getString("friends8");
                String f9=rs.getString("friends9");
                String f10=rs.getString("friends10");
                if(f1.equals(fr)||f2.equals(fr)||f3.equals(fr)||f4.equals(fr)||f5.equals(fr)||f6.equals(fr)||f7.equals(fr)||f8.equals(fr)||f9.equals(fr)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
        return false;
    }
}
