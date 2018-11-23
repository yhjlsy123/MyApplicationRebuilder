package com.isgala.xishuashua.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.isgala.xishuashua.R;
import com.isgala.xishuashua.base.BAdapter;
import com.isgala.xishuashua.base.ViewHolder;
import com.isgala.xishuashua.bean_.School;

import java.util.List;

/**
 * 学校列表
 * Created by and on 2016/11/7.
 */

public class SchoolListAdapter extends BAdapter<School.SchoolItem> {
    public SchoolListAdapter(List<School.SchoolItem> list, int layoutId, Context context) {
        super(list, layoutId, context);
    }

    @Override
    public void upDateView(int position, ViewHolder holder, School.SchoolItem item) {
        if (position == getCount()-1) {
            holder.getView(R.id.school_downline, ImageView.class).setPadding(0, 0, 0, 0);
        } else {
            holder.getView(R.id.school_downline, ImageView.class).setPadding(20, 0, 0, 0);
        }
        holder.getView(R.id.school_name, TextView.class).setText(item.name);
    }
}
