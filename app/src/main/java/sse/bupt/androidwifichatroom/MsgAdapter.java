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
 * Created by lang22 on 2019/7/8.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<MsgQ> mMsgList;


    static class  ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg, rightMsg;
        ImageView left_tx,right_tx;
        public ViewHolder(View view){
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            left_tx = (ImageView) view.findViewById(R.id.left_tx);;
            right_tx = (ImageView) view.findViewById(R.id.right_tx);
        }
    }

    public MsgAdapter(List<MsgQ> msgList){
        this.mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.message_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MsgQ msg = mMsgList.get(position);
        if (msg.getType()== MsgQ.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
//            holder.left_tx.setImageDrawable(msg.getMyTX().getDrawable());
        } else if (msg.getType()== MsgQ.TYPE_SEND){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
//            holder.right_tx.setImageDrawable(MyInfo.getMyTX().getDrawable());
        }

    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

}
