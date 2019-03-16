package com.crec.shield.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.utils.City;
import com.crec.shield.utils.T;

import java.util.List;

import cc.solart.turbo.BaseTurboAdapter;
import cc.solart.turbo.BaseViewHolder;

public class CityVedioAdapter extends BaseTurboAdapter<City, BaseViewHolder> {

    public CityVedioAdapter(Context context) {
        super(context);
    }

    Context context;
    public CityVedioAdapter(Context context, List<City> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected int getDefItemViewType(int position) {
        City city = getItem(position);
        return city.type;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new CityHolder(inflateItemView(R.layout.item_vedio_city, parent));
        else
            return new PinnedHolder(inflateItemView(R.layout.item_pinned_header, parent));
    }


    @Override
    protected void convert(BaseViewHolder holder, City item) {
        if (holder instanceof CityHolder) {
            ((CityHolder) holder).city_name.setText(item.name);

            //增加一个点击城市具体内容的监听
            ((CityHolder) holder).city_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //后期增加跳转再解开
                    //intent.setClass(context, VedioListForCityActivity.class);
                    //intent.putExtra("lineID", item.lineID);
                    //context.startActivity(intent);
                    T.showLong(context, item.lineID);
                }
            });
        }else {
            //String letter = item.pys.substring(0, 1);
            String letter = item.name;
            ((PinnedHolder) holder).city_tip.setText(letter);
        }
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < getData().size(); i++){
            if(getData().get(i).type ==1 && getData().get(i).pys.equals(letter)&&!getData().get(i).name.equals("关注项目")){
                return i;
            }
        }
        return -1;
    }

    class CityHolder extends BaseViewHolder {

        TextView city_name;
        public CityHolder(View view) {
            super(view);
            city_name = findViewById(R.id.city_name);
        }
    }


    class PinnedHolder extends BaseViewHolder {

        TextView city_tip;

        public PinnedHolder(View view) {
            super(view);
            city_tip = findViewById(R.id.city_tip);
        }
    }
}
