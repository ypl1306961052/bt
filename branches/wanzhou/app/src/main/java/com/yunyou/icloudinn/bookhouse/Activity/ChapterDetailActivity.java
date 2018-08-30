package com.yunyou.icloudinn.bookhouse.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookWriteChapterBean;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;


public class ChapterDetailActivity extends AppCompatActivity {

    private BookWriteChapterBean chapter;
    private int currentSequence;
    private int maxSequence;

    private TextView txvContent, txvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        txvTitle = (TextView) findViewById(R.id.title);
        txvContent = (TextView) findViewById(R.id.txv_chapter_content);

        chapter = (BookWriteChapterBean) getIntent().getSerializableExtra("chapter");
        txvTitle.setText("第" + chapter.getSequence() + "章");
        maxSequence = getIntent().getIntExtra("max_sequence", 0);
        txvContent.setText(chapter.getContent());
        currentSequence = chapter.getSequence();

    }

    /**
     * 上一章
     *
     * @param v
     */
    public void doPrev(View v) {
        if (currentSequence > 1) {
            getDate(currentSequence - 1);
        } else {
            ToastUtils.showLongToast("已经到第一章了!");
        }
    }

    /**
     * 下一章
     *
     * @param v
     */
    public void doNext(View v) {
        if (currentSequence < maxSequence) {
            getDate(currentSequence + 1);
        } else {
            ToastUtils.showLongToast("已经到最后一章了!");
        }

    }

    private void getDate(int sequence) {
        String url = Api.WRITE_BOOK_FIND + "?book_write_id=" + chapter.getBookWriteId() + "&sequence=" + sequence;
        OkHttpUtils.get().url(url).build().execute(new JsonCallback<BookWriteChapterBean>(this, true) {
            @Override
            public void onResponse(BookWriteChapterBean data, int id) {
                if (data == null){
                    ToastUtils.showLongToast("无数据");
                    return;
                }
                chapter = data;
                txvTitle.setText("第" + chapter.getSequence() + "章");
                txvContent.setText(chapter.getContent());
                currentSequence = chapter.getSequence();
            }
        });
    }

}
