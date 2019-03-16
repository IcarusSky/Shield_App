package com.crec.shield.adapter.overview.camera;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.entity.common.IsAttentionEntity;
import com.crec.shield.entity.overview.camera.CameraResponse;
import com.crec.shield.entity.project.camera.Camera;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.lzy.okgo.OkGo;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.ADD_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_ADD_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_REMOVE_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.REMOVE_CAMERA_ATTENTION_TOAST;

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Map<String, List<CameraResponse.CameraEntity>> datasets;
    public Context mContext;
    private TextView parentText;
    private ImageView cameraArrow;
    private String PList;
    private String mToken;
    private  List<Camera>camera_list;

    public MyExpandableListViewAdapter(Context mContext, String mToken, Map<String, List<CameraResponse.CameraEntity>> datasets, String PList, List<Camera>camera_list) {
        this.datasets = datasets;
        this.mToken = mToken;
        this.mContext = mContext;
        this.PList = PList;
        this.camera_list = camera_list;
    }


    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
        return datasets.get(PList).get(childPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        if (datasets == null) {
            Toast.makeText(mContext, "dataset为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return datasets.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        if (datasets.get(PList) == null) {
            Toast.makeText(mContext, "\" + parentList[parentPos] + \" + 数据为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return datasets.get(PList).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return datasets.get(PList);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_camera_parent, null);
        }
        view.setTag(R.layout.activity_camera_parent, parentPos);
        view.setTag(R.layout.activity_camera_child, -1);
        parentText = (TextView) view.findViewById(R.id.cameras);
        cameraArrow = (ImageView) view.findViewById(R.id.camera_arrow);
        //ImageView parent_img = view.findViewById(R.id.parent_img);
        // 设置展开和收缩的文字颜色
        if (b) {
            parentText.setTextColor(Color.parseColor("#5A7BEF"));
            cameraArrow.setImageResource(R.mipmap.arrow_camera_up);
        } else {
            parentText.setTextColor(Color.BLACK);
            cameraArrow.setImageResource(R.mipmap.arrow_camera_down);
        }
        parentText.setText(PList);
        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int parentPos, final int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_camera_child, null);
        }
        view.setTag(R.layout.activity_camera_parent, parentPos);
        view.setTag(R.layout.activity_camera_child, childPos);
        TextView text_area = (TextView) view.findViewById(R.id.child_area);
        TextView text_project = (TextView) view.findViewById(R.id.child_project);
        TextView text_line = (TextView) view.findViewById(R.id.child_line);
        TextView text_camera = (TextView) view.findViewById(R.id.child_camera);
        final ImageView isAttention = (ImageView) view.findViewById(R.id.attention);
        text_area.setText(datasets.get(PList).get(childPos).getArea());
        text_project.setText(datasets.get(PList).get(childPos).getProject());
        text_line.setText(datasets.get(PList).get(childPos).getLine());
        text_camera.setText(datasets.get(PList).get(childPos).getLocation());
        if (datasets.get(PList).get(childPos).getIsAttention() == 1) {
            isAttention.setImageResource(R.mipmap.img_star);
            isAttention.setTag("select");
        } else {
            isAttention.setImageResource(R.mipmap.img_star_none);
            isAttention.setTag("unSelect");
        }

        isAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAttention.getTag().equals("select") && datasets.get(PList).get(childPos).getId() != null) {
                    OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_REMOVE_CAMERA_ATTENTION)     // 摄像机取消关注
                            .params("token", mToken)
                            .params("cameraId", datasets.get(PList).get(childPos).getId())
                            .params("cameraChannel", camera_list.get(childPos).getChannel())
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if(isAttentionEntity.getCode()==1){
                                        if(isAttentionEntity.isData()){
                                            isAttention.setTag("unSelect");
                                            isAttention.setImageResource(R.mipmap.img_star_none);
                                            Toast.makeText(mContext, REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(mContext, FAILED_REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                } else if(isAttention.getTag().equals("unSelect") && datasets.get(PList).get(childPos).getId() != null){
                    OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_ADD_CAMERA_ATTENTION)    //  摄像机关注
                            .params("token", mToken)
                            .params("cameraId", datasets.get(PList).get(childPos).getId())
                            .params("cameraChannel", camera_list.get(childPos).getChannel())
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if(isAttentionEntity.getCode()==1){
                                        if(isAttentionEntity.isData()){
                                            isAttention.setTag("select");
                                            isAttention.setImageResource(R.mipmap.img_star);
                                            Toast.makeText(mContext, ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(mContext, FAILED_ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}