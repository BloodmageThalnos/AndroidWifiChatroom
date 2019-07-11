package sse.bupt.androidwifichatroom;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lang22 on 2019/7/10.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> FriendList;


    static class  ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout list_item;
        ImageView tx;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            list_item = (LinearLayout) itemView.findViewById(R.id.group_chat_room);
            tx = (ImageView)itemView.findViewById(R.id.friend_tx);
            name = (TextView)itemView.findViewById(R.id.friend_name);
        }
    }

    public FriendAdapter(List<Friend> friendList){
        this.FriendList = friendList;
    }

    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.room_item,parent,false);
        return new FriendAdapter.ViewHolder(view);
    }
    //定义监听事件接口
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    //声明一个监听事件
    private OnItemClickListener mOnItemClickListener;
    //定义set方法
    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final FriendAdapter.ViewHolder holder, int position) {
        Friend friend = FriendList.get(position);
//        holder.tx.setImageDrawable(friend.getTx().getDrawable());
        holder.name.setText(friend.getName());

        View itemView = (LinearLayout)holder.list_item;
        //添加点击事件
        if(mOnItemClickListener != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return FriendList.size();
    }
}
