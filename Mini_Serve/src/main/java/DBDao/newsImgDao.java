package DBDao;

import Bean.newsImg;

public interface newsImgDao {
    public void addImage(String url,String title);//本地添加图片
    public void readImg(String targetUrl, int ID);//数据库读取图片通过id获取
    public String getTitleById(int id);//获得图片字幕信息
}
