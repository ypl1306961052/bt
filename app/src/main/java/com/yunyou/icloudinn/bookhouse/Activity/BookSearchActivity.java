package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Adapter.BookSearchResultAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.SearchView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class BookSearchActivity extends BaseActivity {

    private String  writeBookId;
    private RecyclerView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        ((TextView)findViewById(R.id.title)).setText("搜索书籍");
        listView = (RecyclerView)findViewById(R.id.list_search_rerult);
        searchView = (SearchView)findViewById(R.id.book_search_view);

        // 设置搜索文本监听
        searchView.setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                search(searchView.getText().toString());
                searchView.setText("");
            }
        });

    }

    private void search(String keyWord){
        OkHttpUtils.get().url(Api.BOOK_SEARCH + keyWord).build().execute(new JsonCallback<List<BookData>>(this,true) {
            @Override
            public void onResponse(List<BookData> bookList, int id) {
                BookSearchResultAdapter adapter = new BookSearchResultAdapter(getApplicationContext(),bookList);
                listView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //设置布局管理器
                listView.setAdapter(adapter);
            }
        });
    }

}
