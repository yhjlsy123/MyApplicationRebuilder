package com.zhuochi.hydream.bathhousekeeper.activity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.zhuochi.hydream.bathhousekeeper.R;
import com.zhuochi.hydream.bathhousekeeper.base.BaseAutoActivity;
import com.zhuochi.hydream.bathhousekeeper.config.AppManager;
import com.zhuochi.hydream.bathhousekeeper.config.Constants;
import com.zhuochi.hydream.bathhousekeeper.dialog.LoadingAnimDialog;
import com.zhuochi.hydream.bathhousekeeper.dialog.LoadingTrAnimDialog;
import com.zhuochi.hydream.bathhousekeeper.dialog.SureSchoolChoice;
import com.zhuochi.hydream.bathhousekeeper.entity.SchoolAreaFloorEntity;
import com.zhuochi.hydream.bathhousekeeper.entity.SchoolAreaRoomEntity;
import com.zhuochi.hydream.bathhousekeeper.entity.SchoolBasicsEntity;
import com.zhuochi.hydream.bathhousekeeper.entity.SonBaseEntity;
import com.zhuochi.hydream.bathhousekeeper.http.GsonUtils;
import com.zhuochi.hydream.bathhousekeeper.http.XiRequestParams;
import com.zhuochi.hydream.bathhousekeeper.utils.LogUtils;
import com.zhuochi.hydream.bathhousekeeper.utils.SPUtils;
import com.zhuochi.hydream.bathhousekeeper.utils.ToastUtils;
import com.zhuochi.hydream.bathhousekeeper.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.zhuochi.hydream.bathhousekeeper.bean_.CheckPWD;
//import com.zhuochi.hydream.bathhousekeeper.bean_.SchoolArea;


/**
 * 选择楼号
 * 注：需要外部传一个s_id
 * Created by and on 2016/11/7.
 */

public class SchoolAreaList extends BaseAutoActivity implements View.OnClickListener {

    @BindView(R.id.schoolarealist)
    ExpandableListView schoolarealist;
    private String TAG = "SchoolAreaList";
    private XiRequestParams params;
    private static List<SchoolAreaRoomEntity> entity;
    private List<SchoolAreaFloorEntity> floorEntities;
    private int mGroupPosition;
    private int ORG_AREA_ID, ORG_ID;
    ImageView userinfo_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolarea);
        ButterKnife.bind(this);
        userinfo_cancle = (ImageView) findViewById(R.id.userinfo_cancle);
        userinfo_cancle.setOnClickListener(this);
        params = new XiRequestParams(this);
        initView();
        getBuildings();

    }

    /**
     * 获取楼号列表
     */
    private void getBuildings() {
        ORG_ID = Integer.valueOf(getIntent().getStringExtra("ORG_ID"));//学校
        ORG_AREA_ID = Integer.valueOf(getIntent().getStringExtra("ORG_AREA_ID"));//校区
        params.addCallBack(this);
        // params.getBuildings(ORG_AREA_ID);
        params.getOrgBathroomLists(SPUtils.getInt(Constants.USER_ID, 0));
    }

    /**
     * 获取服务区域列表（浴室）
     */
    private void getDeviceArea(int buildingid) {
        params.addCallBack(this);
        // params.getDeviceArea(ORG_AREA_ID, buildingid);
    }

    @Override
    public void onRequestSuccess(String tag, SonBaseEntity result) {
        switch (tag) {
            case "getBuildings":
                JSONArray jsonArray = new JSONArray((ArrayList) result.getData().getData());
                if (jsonArray.size() > 0) {
                    floorEntities = JSON.parseArray(JSON.toJSONString(jsonArray), SchoolAreaFloorEntity.class);
                    updateList(floorEntities);
                } else {
                    ToastUtils.show(result.getData().getMsg());
                }
                break;
            case "getDeviceArea":
                JSONArray jsonArrays = new JSONArray((ArrayList) result.getData().getData());
                if (jsonArrays.size() > 0) {
                    entity = JSON.parseArray(JSON.toJSONString(jsonArrays), SchoolAreaRoomEntity.class);


                    int count = schoolarealist.getExpandableListAdapter().getGroupCount();
                    for (int j = 0; j < count; j++) {
                        if (j == mGroupPosition) {
                            if (j != mExpandPosition) {
                                mExpandPosition = mGroupPosition;
//                             记录展开的位置
                                schoolarealist.expandGroup(mGroupPosition);
                            } else {
                                mExpandPosition = -1;
                                schoolarealist.collapseGroup(mGroupPosition);
                            }
                        } else {
                            schoolarealist.collapseGroup(j);
                        }
                    }
                } else {
                    ToastUtils.show(result.getData().getMsg());
                }
                break;
            case "setDeviceInfo":
                Map map = (Map) result.getData().getData();
                try {
                    String gson = GsonUtils.parseMapToJson(map);
                    SchoolBasicsEntity entity = JSON.parseObject(gson, SchoolBasicsEntity.class);
                    UserUtils.setSchoolbase();
                    SPUtils.saveInt(Constants.ORG_ID, entity.getDefault_org_id());//学校ID
                    SPUtils.saveInt(Constants.ORG_AREA_ID, entity.getDefault_org_area_id());//校区ID
                    SPUtils.saveInt(Constants.BUILDING_ID, entity.getDefault_building_id());//楼层ID
                    SPUtils.saveInt(Constants.DEVICE_AREA_ID, entity.getDefault_device_area_id());//当前绑定区域（浴室）
                    //判断当前是否需要认证
                    LoadingAnimDialog.dismissLoadingAnimDialog();
                    // check();
                    //关闭选择校区界面
                    finish();
                    AppManager.INSTANCE.finishActivity(SchoolList.class);
                    AppManager.INSTANCE.finishActivity(SchoolOrgList.class);
                    //AppManager.INSTANCE.finishActivity(CampusInformationActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

        super.onRequestSuccess(tag, result);
    }


    private int mExpandPosition = -1;

    private void initView() {
        schoolarealist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int mid = floorEntities.get(groupPosition).getBuilding_id();
                mGroupPosition = groupPosition;
                getDeviceArea(mid);
                return true;
            }
        });


        schoolarealist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String area_id = String.valueOf(floorEntities.get(groupPosition).getBuilding_id());
                String area_name = floorEntities.get(groupPosition).getBuilding_no();
                String Building_id = String.valueOf(entity.get(childPosition).getDevice_area_id());
                String Building_no = entity.get(childPosition).getDevice_area_name();
                showDialog(area_id, Building_id, area_name, Building_no);
                return true;
            }
        });
    }

    /**
     * 弹出确认选择的提示框
     *
     * @param campus
     * @param b_id
     */
    private void showDialog(final String campus, final String b_id, String name, final String room) {
        SureSchoolChoice.Builder builder = new SureSchoolChoice.Builder(this);
        builder.setOnClickListener(new SureSchoolChoice.Listener() {
            @Override
            public void sure() {
                modify(campus, b_id);
            }
        });
        builder.create("已选择" + name, room).show();
    }

    /**
     * 修改校区
     *
     * @param campus
     * @param b_id
     */
    private void modify(final String campus, final String b_id) {
        int area_id = Integer.valueOf(campus);//楼层ID
        int room_id = Integer.valueOf(b_id);//浴室
        LoadingAnimDialog.showLoadingAnimDialog(SchoolAreaList.this);
        params.addCallBack(this);
        // params.setDeviceInfo(UserUtils.getInstance(this).getMobilePhone(), ORG_ID, ORG_AREA_ID, area_id, room_id);

//        HashMap<String, String> map = new HashMap<>();
//        map.put("campus", campus);
//        map.put("b_id", b_id);
//        map.put("s_id", s_id);
//        VolleySingleton.post(Neturl.MODIFY_BATHROOM, "modify_campus", map, new VolleySingleton.CBack() {
//            @Override
//            public void onRequestSuccess(String result) {
//                Result bean = JsonUtils.parseJsonToBean(result, Result.class);
//                if (bean != null && TextUtils.equals("1", bean.status)) {
//                    SPUtils.saveString(Constants.S_ID, s_id);
//                    SPUtils.saveString(Constants.CAMPUS, campus);
//                    SPUtils.saveString("b_id", b_id);
//                    SPUtils.saveString("bathroomLoaction", room);
//                    checkAuthe();
//                } else {
//                    LoadingAnimDialog.dismissLoadingAnimDialog();
//                }
//            }
//        });

    }

    //如果栈里不包含HomeActivity 便启动一个
   /* private void check() {
        if (!AppManager.INSTANCE.containActivity(HomeActivity.class)) {
            startActivity(new Intent(SchoolAreaList.this, HomeActivity.class));
        }
    }*/

    /**
     * 检查是否需要实名认证
     */
    private void checkAuthe() {
//        VolleySingleton.post(Neturl.CHECK_LOCK_PWD, "checkauthe", new HashMap<String, String>(), new VolleySingleton.CBack() {
//            @Override
//            public void onRequestSuccess(String result) {
//                CheckPWD bean = JsonUtils.parseJsonToBean(result, CheckPWD.class);
//                if (bean != null && bean.data != null) {
//                    String device_type = bean.data.device_type;
//                    SPUtils.saveString(Constants.DEVICE_TYPE, device_type);
//                    if (TextUtils.equals("1", bean.data.need_auth) && TextUtils.equals("0", bean.data.auth.result)) {//需要认证，却没认证
//                        Intent intent = new Intent(SchoolAreaList.this, AutheActivity.class);
//                        startActivity(intent);
//                    } else {
//                        check();
//                    }
//                    //关闭选择校区界面
//                    finish();
//                    AppManager.INSTANCE.finishActivity(SchoolList.class);
//                }
//                LoadingAnimDialog.dismissLoadingAnimDialog();
//            }
//        });
    }

    private SchoolExpandAdapter schoolAreaAdapter;

    private void updateList(List<SchoolAreaFloorEntity> data) {
        schoolAreaAdapter = new SchoolExpandAdapter(data);
        schoolarealist.setAdapter(schoolAreaAdapter);
    }

    private String s_id;

    private void initData() {
        LoadingTrAnimDialog.showLoadingAnimDialog(this);
        HashMap<String, String> map = new HashMap<>();
        s_id = getIntent().getStringExtra("ORG_ID");
        LogUtils.e("s_id", s_id);
        if (TextUtils.isEmpty(s_id)) {
            s_id = "";
        }
        map.put("s_id", s_id);
        map.put(Constants.LOCATON_LAT, SPUtils.getString(Constants.LOCATON_LAT, ""));
        map.put(Constants.LOCATON_LNG, SPUtils.getString(Constants.LOCATON_LNG, ""));

    }

//    @OnClick(R.id.schoolarea_back)
//    public void onClick() {
//        finish();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userinfo_cancle:
                finish();
                break;
        }

    }

    public static class SchoolExpandAdapter implements ExpandableListAdapter {
        public List<SchoolAreaFloorEntity> getList() {
            return list;
        }

        private List<SchoolAreaFloorEntity> list;

        public SchoolExpandAdapter(List<SchoolAreaFloorEntity> data) {
            list = new ArrayList<>();
            list.addAll(data);
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return entity.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_schoolarea, null);
            }
            TextView groupTextView = (TextView) convertView.findViewById(R.id.group_schoolarea);
            String name = list.get(groupPosition).getBuilding_no();
            groupTextView.setText(name);
            ImageView arrow = (ImageView) convertView.findViewById(R.id.expand_arrow);
            arrow.setImageResource(isExpanded ? R.mipmap.xuanzexiaoqu_shangla : R.mipmap.xuanzexiaoqu_xiala);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_schoolarea, null);
            }
            TextView groupTextView = (TextView) convertView.findViewById(R.id.child_schoolarea);
//            Room room = list.get(groupPosition).room.get(childPosition);
            if (!entity.get(childPosition).getDevice_area_name().isEmpty()) {
                groupTextView.setText(entity.get(childPosition).getDevice_area_name());
            }
            ImageView line = (ImageView) convertView.findViewById(R.id.schoolarea_child_line);
            if (childPosition == getChildrenCount(groupPosition) - 1) {
                line.setPadding(0, 0, 0, 0);
            } else {
                line.setPadding(90, 0, 0, 0);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
//            LogUtils.e(TAG, "onGroupExpanded" + groupPosition);
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
//            LogUtils.e(TAG, "onGroupCollapsed" + groupPosition);
        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return childId;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return groupId;
        }
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
