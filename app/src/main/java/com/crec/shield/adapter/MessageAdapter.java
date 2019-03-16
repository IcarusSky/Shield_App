package com.crec.shield.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crec.shield.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.AuthorViewHolder>{
    private List<String> mData1;
    private List<String> mData2;
    private List<String> mData3;
    private List<Integer> mData4;
    private List<Integer> images;
    private List<String> project;
    private List<String> line;
    private Context mContext;
    private MyItemClickListener mItemClickListener;
    private Integer flag = 0;

    public MessageAdapter(Context context, List<String> data1, List<String> data2, List<String> data3, List<Integer> data4,List<Integer> image,List<String> project,List<String> line){
        this.mContext=context;
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
        this.mData4 = data4;
        this.images = image;
        this.project = project;
        this.line = line;
    }

    //holder初始化
    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.activity_message_item, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView, mItemClickListener);
        return viewHolder;
    }

    //数据持久化
    @Override
    public void onBindViewHolder(final AuthorViewHolder holder, final int position) {
        holder.warnType.setText(mData1.get(position));
        holder.warnTime.setText(mData2.get(position));
        holder.warnProject.setText(mData3.get(position));
        holder.warnImage.setImageResource(images.get(position));
        if (images.get(position) == R.mipmap.img_msg_item){
            holder.warnType.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
        }else {
            holder.warnType.setTextColor(ContextCompat.getColor(mContext, R.color.text_com_color));
        }
        //holder.warnImage.setImageResource(R.mipmap.message_read);

        /*holder.warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (images.get(position) == R.mipmap.message_unread){
                    images.set(position, R.mipmap.message_read);
                    //flag++;
                }
            }
        });*/
    }

    //计数
    @Override
    public int getItemCount() {

        return mData1 == null ? 0 : mData1.size();
    }

    //绑定
    class AuthorViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView warnType;
        TextView warnTime;
        TextView warnProject;
        ImageView warnImage;
        LinearLayout warning;

        private MyItemClickListener mListener;

        public AuthorViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);

            warnType=(TextView) itemView.findViewById(R.id.warn_type);
            warnTime=(TextView) itemView.findViewById(R.id.warn_time);
            warnProject=(TextView) itemView.findViewById(R.id.warn_project);
            warnImage = (ImageView) itemView.findViewById(R.id.warn_image);
            warning =(LinearLayout) itemView.findViewById(R.id.warning);

            /*warnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    warnImage.setImageResource(R.mipmap.message_unread);
                }
            });*/

            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * 实现OnClickListener接口重写的方法
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}
