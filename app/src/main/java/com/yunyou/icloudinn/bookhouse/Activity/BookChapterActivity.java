package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Adapter.ChapterAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookWriteBean;
import com.yunyou.icloudinn.bookhouse.R;
import com.zhy.http.okhttp.OkHttpUtils;

public class BookChapterActivity extends BaseActivity {

    private String  writeBookId;
    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_chapter);
        ((TextView)findViewById(R.id.title)).setText("章节目录");
        listView = (RecyclerView)findViewById(R.id.list_chapter);
        writeBookId = getIntent().getStringExtra("write_book_id");
        if(writeBookId==null||writeBookId=="")return;

        OkHttpUtils.get().url(Api.WRITE_BOOK+"/"+writeBookId).build().execute(new JsonCallback<BookWriteBean>(this,true) {
            @Override
            public void onResponse(BookWriteBean bookWrite, int id) {
                ChapterAdapter adapter = new ChapterAdapter(getApplicationContext(),bookWrite);
                listView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //设置布局管理器
                listView.setAdapter(adapter);
            }
        });
    }


}
