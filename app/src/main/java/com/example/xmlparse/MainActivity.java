package com.example.xmlparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        xmlParse();
    }

    private void setAdapter(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommonAdapter<NewsBean>(getApplicationContext(),R.layout.item,datas) {
            @Override
            protected void convert(ViewHolder holder, NewsBean NewsBean, int position) {
                ImageView ivCover = holder.getView(R.id.iv_cover);
                Glide.with(getApplicationContext()).load(NewsBean.getImageUrl().trim()).into(ivCover);

                Log.i("TAG",NewsBean.getImageUrl());

                holder.setText(R.id.tv_title,NewsBean.getName());
                holder.setText(R.id.tv_content,NewsBean.getContent());
                holder.setText(R.id.tv_num,"字数 " + NewsBean.getLength());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void xmlParse(){
        datas = PullParser.pullParse(getApplicationContext());
        adapter.notifyDataSetChanged();
    }

    private int xmlToJson() {
        try {
            InputStream inputStream = new FileInputStream("");
            XmlToJson xmlToJson = new XmlToJson.Builder(inputStream, null).build();
            JSONObject jsonObject = xmlToJson.toJson();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
