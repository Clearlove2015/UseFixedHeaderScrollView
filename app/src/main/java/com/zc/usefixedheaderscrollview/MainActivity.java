package com.zc.usefixedheaderscrollview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.zc.usefixedheaderscrollview.adapter.MyAdapter;
import com.zc.usefixedheaderscrollview.util.InitDataUtil;
import com.zc.usefixedheaderscrollview.util.ObservableScrollView;
import com.zc.usefixedheaderscrollview.util.TabLayoutUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ObservableScrollView.OnObservableScrollViewScrollChanged {
    @Bind(R.id.tab_topView)
    TabLayout tab_topView;
    @Bind(R.id.ll_topView)
    LinearLayout ll_topView;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sv_contentView)
    ObservableScrollView sv_contentView;
    @Bind(R.id.ll_fixedView)
    LinearLayout ll_fixedView;
    @Bind(R.id.line_h)
    View lineH;

    //用来记录内层固定布局到屏幕顶部的距离
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        initTab(tab_topView);
        initRV();

    }

    public void init() {
        sv_contentView.setOnObservableScrollViewScrollChanged(this);
    }

    public void initTab(TabLayout tab) {
        TabLayoutUtils.setIndicator(tab_topView, 20, 20);
        tab.addTab(tab.newTab().setText("TAB1"));
        tab.addTab(tab.newTab().setText("TAB2"));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initRV() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);//解决嵌套滑动卡顿问题
        rv.setFocusable(false);//解决嵌套默认显示不在顶部问题
        rv.setAdapter(new MyAdapter(InitDataUtil.getData()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //获取HeaderView的高度，当滑动大于等于这个高度的时候，需要把topView移除当前布局，放入到外层布局
            mHeight = ll_topView.getTop();
        }
    }

    /**
     * @param l    Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t    Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    public void onObservableScrollViewScrollChanged(int l, int t, int oldl, int oldt) {
        if (t >= mHeight) {
            if (tab_topView.getParent() != ll_fixedView) {
                ll_topView.removeView(tab_topView);
                ll_topView.removeView(lineH);
                ll_fixedView.addView(tab_topView);
                ll_fixedView.addView(lineH);
            }
        } else {
            if (tab_topView.getParent() != ll_topView) {
                ll_fixedView.removeView(tab_topView);
                ll_fixedView.removeView(lineH);
                ll_topView.addView(tab_topView);
                ll_topView.addView(lineH);
            }
        }
    }
}
