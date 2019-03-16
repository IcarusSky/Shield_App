package com.crec.shield.adapter.overview.camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crec.shield.R;

import java.util.List;

public class CameraSelectAdapter extends BaseAdapter{

    private List<String> Datas;
    private Context mContext;
    private int checkItemPosition = 0;

    public CameraSelectAdapter(Context mContext, List<String> datas) {
        Datas = datas;
        this.mContext = mContext;
    }

    /**
     * 返回item的个数
     * @return
     */
    @Override
    public int getCount() {
        return Datas.size();
    }

    /**
     * 返回每一个item对象
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return Datas.get(i);
    }

    /**
     * 返回每一个item的id
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vHolder;

        if(convertView==null){
            //初始化item布局
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_camera_selectitem,parent,false);

            //创建记事本类
            vHolder = new  ViewHolder();

            //查找控件，保存控件的引用
            vHolder.tv = ((TextView) convertView.findViewById(R.id.name));

            //将当前viewHolder与converView绑定
            convertView.setTag(vHolder);
        }else{
            //如果不为空，获取
            vHolder = (ViewHolder) convertView.getTag();
        }
        vHolder.tv.setText(Datas.get(position));

        return convertView;
    }

    class ViewHolder{
        TextView tv;
    }

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

}
