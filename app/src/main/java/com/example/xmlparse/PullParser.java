package com.example.xmlparse;

import android.content.Context;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libo on 2017/12/28.
 */

public class PullParser {

    public static List<NewsBean> pullParse(Context context){
        ArrayList<NewsBean> list = new ArrayList<>();
        NewsBean NewsBean = null;
        try {
            //获取资源文件流
            InputStream inputStream = context.getResources().openRawResource(R.raw.news);
            XmlPullParser parser = Xml.newPullParser();
            //设置文件流和编码方式
            parser.setInput(inputStream,"UTF-8");

            //得到解析的事件类型
            int eventType = parser.getEventType();

            //循环读取标签知道文档读取结束
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        //item起始标签，创建对象
                        String tag = parser.getName();
                        if(tag.equalsIgnoreCase("item")){
                            NewsBean = new NewsBean();
                            NewsBean.setName(parser.getAttributeValue(null,"name"));
                            NewsBean.setLength(Integer.parseInt(parser.getAttributeValue(null,"textnum")));
                        }else if(tag.equalsIgnoreCase("content")){
                            NewsBean.setContent(parser.nextText());
                        }else if(tag.equalsIgnoreCase("imageurl")){
                            NewsBean.setImageUrl(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //遇到对象结束标签，将对象添加到集合中
                        if(parser.getName().equalsIgnoreCase("item") && NewsBean != null){
                            list.add(NewsBean);
                            NewsBean = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
