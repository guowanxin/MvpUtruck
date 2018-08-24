package tdh.ifm.android.imatch.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.bean.MyMessage;
import tdh.ifm.android.imatch.app.utils.Constants;

/**
 * Created by tdh on 2017/5/5.
 */

public class MessageTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyMessage> myMessages;

    public MessageTwoAdapter(Context context){
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_message_two, parent, false);
        // 创建一个ViewHolder
        MessageTwoAdapter.MyViewHolder holder = new MessageTwoAdapter.MyViewHolder(view);
        return holder;
    }
    public void setData(List<MyMessage> myMessages){
        this.myMessages = myMessages;
        this.notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyMessage myMessage=myMessages.get(position);

        if(Constants.classCd_one.equals(myMessage.getClassCd())) {
            if (!TextUtils.isEmpty(myMessage.getMessage())) {
                ((MyViewHolder) holder).tvMessage.setText(myMessage.getMessage());
            }
            if (!TextUtils.isEmpty(myMessage.getCreatedTime())){
                ((MyViewHolder) holder).tvMessageDetail.setText(myMessage.getCreatedTime());
            }
        }else if(Constants.classCd_two.equals(myMessage.getClassCd())){
            if (!TextUtils.isEmpty(myMessage.getCategoryName())) {
                ((MyViewHolder) holder).tvMessage.setText(myMessage.getCategoryName());
            }
            if (!TextUtils.isEmpty(myMessage.getCategoryName())){
                ((MyViewHolder) holder).tvMessageDetail.setText(myMessage.getCategoryName());
            }
        }else if(Constants.classCd_three.equals(myMessage.getClassCd())){
            if (!TextUtils.isEmpty(myMessage.getCategoryName())) {
                ((MyViewHolder) holder).tvMessage.setText(myMessage.getCategoryName());
            }
            if (!TextUtils.isEmpty(myMessage.getMessage())){
                ((MyViewHolder) holder).tvMessageDetail.setText(myMessage.getMessage());
            }
        }else if(Constants.classCd_four.equals(myMessage.getClassCd())){
            if (!TextUtils.isEmpty(myMessage.getCategoryName())) {
                ((MyViewHolder) holder).tvMessage.setText(myMessage.getCategoryName());
            }
            if (!TextUtils.isEmpty(myMessage.getMessage())){
                ((MyViewHolder) holder).tvMessageDetail.setText(myMessage.getMessage());
            }
        }

    }

    @Override
    public int getItemCount() {
        if (myMessages == null) {
            return 0;
        }
        return myMessages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_message_detail)
        TextView tvMessageDetail;

        public MyViewHolder(View view) {
            super(view);
            // 给父控件绑定监听器
            AutoUtils.autoSize(view);
            ButterKnife.bind(this, view);
        }
    }
}
