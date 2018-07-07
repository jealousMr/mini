package DBDaoImpl;

import Bean.newsImg;
import DBDao.newsImgDao;
import DBUtil.DB;
import OtherUtil.Base64;
import OtherUtil.ImageUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class newsImgDaoImpl implements newsImgDao {


    @Override
    public void addImage(String url, String title) {
        String path=url;
        Connection conn=null;
        PreparedStatement ps=null;
        FileInputStream in=null;
        try {
            in = ImageUtil.readImage(path);
            conn = DB.open();
            String sql = "insert into newsImg(title,img)values(?,?)";
            ps = (PreparedStatement) conn.prepareStatement(sql);

            ps.setString(1,title);
            ps.setBinaryStream(2, in, in.available());
            // ps.executeUpdate();

            int count = ps.executeUpdate();
            if (count > 0) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.close(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void readImg(String targetUrl, int ID) {
        Connection conn= DB.open();
        try {
            PreparedStatement query = (PreparedStatement) conn.prepareStatement("select * from newsImg where id=?");
            query.setInt(1, ID);
            ResultSet re = query.executeQuery();
            re.next();
            //使用Blob对象接收数据库里的图片
            java.sql.Blob imgBlob = re.getBlob(3);

            //获取Blob对象的二进制流;
            InputStream imgInS = imgBlob.getBinaryStream();
            //文件输出流
            OutputStream imgOuS = new FileOutputStream(new File(targetUrl));

            //下面就把图片写出到文件夹里面
            byte[] buffer = new byte[1024];
            int len;
            while ((len = imgInS.read(buffer)) != -1) {
                imgOuS.write(buffer, 0, len);
            }//图片写出完毕
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
    }

    @Override
    public String getTitleById(int id) {
        String sql="SELECT id,title,img FROM newsImg WHERE id = ?";
        Connection conn= DB.open();
        try {
            PreparedStatement pstmt= (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs=  pstmt.executeQuery();
            if(rs.next()){
                // int id=rs.getInt(1);

                String title=rs.getString(2);

                return title;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.close(conn);
        }
        return null;
    }
    }



