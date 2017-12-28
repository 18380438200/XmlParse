package com.example.xmlparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<NewsBean> datas = new ArrayList<>();
    private CommonAdapter<NewsBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAdapter();
        //xmlParse();
        xmlToJson();
    }

    private void setAdapter(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommonAdapter<NewsBean>(getApplicationContext(),R.layout.item,datas) {
            @Override
            protected void convert(ViewHolder holder, NewsBean NewsBean, int position) {
                ImageView ivCover = holder.getView(R.id.iv_cover);
                Glide.with(getApplicationContext()).load(NewsBean.getImageUrl().trim()).into(ivCover);

                holder.setText(R.id.tv_title,NewsBean.getName());
                holder.setText(R.id.tv_content,NewsBean.getContent());
                holder.setText(R.id.tv_num,"字数 " + NewsBean.getLength());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    /**
     * pull解析方式
     */
    private void xmlParse(){
        List list = PullParser.pullParse(getApplicationContext());
        datas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * xml转json方式
     * @return
     */
    private int xmlToJson() {

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.news);
            XmlToJson xmlToJson = new XmlToJson.Builder(inputStream, null).build();
            JSONArray array = xmlToJson.toJson().getJSONObject("news").getJSONArray("item");
            List list = JSON.parseArray(array.toString(),NewsBean.class);
            datas.addAll(list);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
