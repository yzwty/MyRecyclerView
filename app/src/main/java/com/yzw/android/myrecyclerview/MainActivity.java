package com.yzw.android.myrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowShowingAlways();
        initData();
        mRecyclerView= (RecyclerView) this.findViewById(R.id.recyclerView );
        mAdapter=new MyAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        //设置布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置item分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        // 设置itemz增减时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, String s) {
                Toast.makeText(MainActivity.this,"点击了"+s,Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                mAdapter.addData(1,"A");
                break;
            case R.id.delete:
                mAdapter.removeData(1);
                break;
            case R.id.list_view:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
                break;
            case R.id.grid_view:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
                mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
            break;
            case R.id.stag_view:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));
                mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
                break;
        }

        return true;
    }


    //屏蔽掉物理Menu键，不然在有物理Menu键的手机上，overflow按钮会显示不出来。
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField!=null){
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
