package com.zhuochi.hydream.bathhousekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhuochi.hydream.bathhousekeeper.R;
import com.zhuochi.hydream.bathhousekeeper.base.BaseActivity;
import com.zhuochi.hydream.bathhousekeeper.entity.FeedbackMessageEntity;
import com.zhuochi.hydream.bathhousekeeper.entity.SonBaseEntity;
import com.zhuochi.hydream.bathhousekeeper.http.XiRequestParams;
import com.zhuochi.hydream.bathhousekeeper.utils.Common;
import com.zhuochi.hydream.bathhousekeeper.utils.ToastUtils;
import com.zhuochi.hydream.bathhousekeeper.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 我的反馈列表*/
public class MyFeedBackActivity extends BaseActivity {


    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    private List<FeedbackMessageEntity> mCouponEntityList;
    private XiRequestParams params;
    private MyFeedBackAdater adater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back);
//        highApiEffects();
        ButterKnife.bind(this);
        params = new XiRequestParams(this);
        initData();
        getMyFeedbackList();
    }

    private void initData() {
        toolbarTitle.setText("我的反馈");
    }

    //获取我的反馈列表
    private void getMyFeedbackList() {
        params.addCallBack(this);
        params.myFeedbackList(UserUtils.getInstance(this).getUserID());
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRequestSuccess(String tag, SonBaseEntity result) {
        switch (tag) {
            case "myFeedbackList"://获取我的反馈列表
                try {
                    JSONArray jsonArray = new JSONArray((ArrayList) result.getData().getData());
                    if (jsonArray.size() > 0) {
                        mCouponEntityList = JSON.parseArray(JSON.toJSONString(jsonArray), FeedbackMessageEntity.class);
                        if (adater == null) {
                            adater = new MyFeedBackAdater(this);
                            listView.setAdapter(adater);
                        } else {
                            adater.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtils.show("暂无数据！！！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private class MyFeedBackAdater extends BaseAdapter {

        private Context mcontext;

        private MyFeedBackAdater(Context context) {
            mcontext = context;
        }

        @Override
        public int getCount() {
            return mCouponEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_my_feedback, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            holder.tv_nam.setText(mCouponEntityList.get(position).getContent());
            holder.tv_time.setText(Common.getDateToString(mCouponEntityList.get(position).getCreate_time()));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, FeedbackHistoryListActivity.class);
                    String id = String.valueOf(mCouponEntityList.get(position).getId());
                    String typeid = String.valueOf(mCouponEntityList.get(position).getType_id());
                    intent.putExtra("id", id);
                    intent.putExtra("typeid", typeid);
                    startActivity(intent);
                }
            });

            return convertView;
        }


        class ViewHolder {
            TextView tv_nam;
            TextView tv_time;

            public ViewHolder(View view) {
                tv_nam = (TextView) view.findViewById(R.id.tv_nam);
                tv_time = (TextView) view.findViewById(R.id.tv_time);
            }
        }
    }

    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
